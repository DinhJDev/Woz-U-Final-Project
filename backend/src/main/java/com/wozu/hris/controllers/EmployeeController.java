package com.wozu.hris.controllers;


import com.wozu.hris.models.*;
import com.wozu.hris.payload.request.EmployeeBenefitsRequest;
import com.wozu.hris.payload.request.EmployeeDepartmentRequest;
import com.wozu.hris.payload.request.EmployeeRequest;
import com.wozu.hris.payload.request.EmployeeTrainingsRequest;
import com.wozu.hris.payload.response.MessageResponse;
import com.wozu.hris.repositories.DepartmentEmployeeRepository;
import com.wozu.hris.repositories.DepartmentRepository;
import com.wozu.hris.repositories.EmployeeRepository;
import com.wozu.hris.repositories.EmployeeTrainingRepository;
import com.wozu.hris.services.EmployeeService;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeRepository eRepo;
    @Autowired
    DepartmentEmployeeRepository dERepo;
    @Autowired
    EmployeeTrainingRepository eTRepo;

    // Get all candidates
    @PreAuthorize("hasRole('HR') or hasRole('MANAGER')")
    @GetMapping("/candidates")
    public ResponseEntity<List<Employee>> getAllCandidates(){
        try {
            List<Employee> employees = new ArrayList<Employee>();

            eRepo.findByAccountRolesName(ERole.ROLE_CANDIDATE).forEach(employees::add);

            if(employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all employees
    @PreAuthorize("hasRole('HR') or hasRole('MANAGER')")
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        try {
            List<Employee> employees = new ArrayList<Employee>();

            eRepo.findByAccountRolesName(ERole.ROLE_EMPLOYEE).forEach(employees::add);

            if(employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get all managers

    @PreAuthorize("hasRole('HR') or hasRole('MANAGER')")
    @GetMapping("/managers")
    public ResponseEntity<List<Employee>> getAllManagers(){
        try {
            List<Employee> employees = new ArrayList<Employee>();

            eRepo.findByAccountRolesName(ERole.ROLE_MANAGER).forEach(employees::add);

            if(employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get all hr

    @PreAuthorize("hasRole('HR') or hasRole('MANAGER')")
    @GetMapping("/hr")
    public ResponseEntity<List<Employee>> getAllHR(){
        try {
            List<Employee> employees = new ArrayList<Employee>();

            eRepo.findByAccountRolesName(ERole.ROLE_HR).forEach(employees::add);

            if(employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // create employee rest api
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeService.createEmployee(employee);
    }

    // get employee by id rest api
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER') or hasRole('HR')")
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id){
        Employee employee = employeeService.findEmployee(id);
        return ResponseEntity.ok(employee);
    }

    // update employee details
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id, @RequestBody EmployeeRequest employeeDetails){
        Employee employee = employeeService.findEmployee(id);

        /*
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setDateOfBirth(employeeDetails.getDateOfBirth());*/
        employee.setEmployeeTrainings(employeeDetails.getEmployeeTrainings());
        employee.setBenefit(employeeDetails.getBenefit());

        Employee updatedEmployee = employeeService.updateEmployee(id, employee);

        return  ResponseEntity.ok(updatedEmployee);
    }

    // update employee benefits
    @PutMapping("/employees/{id}/benefits")
    public ResponseEntity<?> updateEmployeeBenefits(@PathVariable("id") Long id, @RequestBody EmployeeBenefitsRequest employeeDetails) {
        Employee employee = employeeService.findEmployee(id);

        employee.setBenefit(employeeDetails.getBenefit());
        employeeService.updateEmployee(id, employee);

        return ResponseEntity.ok("Employee " + employee.getId() + " updated with the following benefit: " + employeeDetails.getBenefit().getName());
    }

    // update employee department
    @PutMapping("/employees/{id}/departments")
    public ResponseEntity<?> updateEmployeeDepartments(@PathVariable("id") Long id, @RequestBody EmployeeDepartmentRequest employeeDepartmentRequest) {
        Employee employee = employeeService.findEmployee(id);
        // List of old Departments
        List<Department> addList = employee.getDepartment().stream().map(DepartmentEmployee::getDepartment).collect(Collectors.toList());
        // List of new Departments
        List<Department> delList = employeeDepartmentRequest.getDepartment();

        // Find what's missing from the old list of departments (New stuff to add)
        addList.removeAll(employeeDepartmentRequest.getDepartment());
        // Find what's missing from the new list of departments (Old stuff to remove)
        delList.removeAll(employee.getDepartment().stream().map(DepartmentEmployee::getDepartment).collect(Collectors.toList()));
        for (int i = 0; i < addList.size(); i++) {
            DepartmentEmployee dE = new DepartmentEmployee(addList.get(i), employee);
            dERepo.save(dE);
        }
        for (int i = 0; i < delList.size(); i++) {
            Optional<DepartmentEmployee> dE = dERepo.findByEmployeeAndDepartment(employee, delList.get(i));
            if (dE.isPresent()) {
                dERepo.delete(dE.get());
            }
        }
        return ResponseEntity.ok(new MessageResponse("Employee " + employee.getId() + "'s departments have been updated."));
    }

    // update employee trainings
    @PutMapping("/employees/{id}/trainings")
    public ResponseEntity<?> updateEmployeeTrainings(@PathVariable("id") Long id, @RequestBody EmployeeTrainingsRequest employeeTrainingsRequest) {
        Employee employee = employeeService.findEmployee(id);
        // List of old Departments
        List<Training> addList = employee.getEmployeeTrainings().stream().map(EmployeeTraining::getTraining).collect(Collectors.toList());
        // List of new Departments
        List<Training> delList = employeeTrainingsRequest.getTrainings();

        // Find what's missing from the old list of departments (New stuff to add)
        addList.removeAll(employeeTrainingsRequest.getTrainings());
        // Find what's missing from the new list of departments (Old stuff to remove)
        delList.removeAll(employee.getEmployeeTrainings().stream().map(EmployeeTraining::getTraining).collect(Collectors.toList()));
        for (int i = 0; i < addList.size(); i++) {
            EmployeeTraining eT = new EmployeeTraining(addList.get(i), employee);
            eTRepo.save(eT);
        }
        for (int i = 0; i < delList.size(); i++) {
            Optional<EmployeeTraining> eT = eTRepo.findByEmployeeAndTraining(employee, delList.get(i));
            if (eT.isPresent()) {
                eTRepo.delete(eT.get());
            }
        }
        return ResponseEntity.ok(new MessageResponse("Employee " + employee.getId() + "'s trainings have been updated."));
    }

    // delete employee rest api
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        Employee employee = employeeService.findEmployee(id);
        employeeService.deleteEmployee(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return  ResponseEntity.ok(response);
    }
}