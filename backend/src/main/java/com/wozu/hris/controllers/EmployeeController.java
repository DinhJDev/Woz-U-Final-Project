package com.wozu.hris.controllers;


import com.wozu.hris.models.ERole;
import com.wozu.hris.models.Employee;
import com.wozu.hris.repositories.EmployeeRepository;
import com.wozu.hris.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeRepository eRepo;

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

    // update employee rest api
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id, @RequestBody Employee employeeDetails){
        Employee employee = employeeService.findEmployee(id);

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setDateOfBirth(employeeDetails.getDateOfBirth());
        employee.setEmployeeTrainings(employeeDetails.getEmployeeTrainings());
        employee.setBenefit(employeeDetails.getBenefit());

        // Finds difference between request List/Set vs existing and makes updates

        Employee updatedEmployee = employeeService.updateEmployee(id, employee);

        return  ResponseEntity.ok(updatedEmployee);
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