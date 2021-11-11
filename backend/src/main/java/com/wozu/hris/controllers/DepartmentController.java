package com.wozu.hris.controllers;

import com.wozu.hris.models.Department;
import com.wozu.hris.models.DepartmentEmployee;
import com.wozu.hris.models.Employee;
import com.wozu.hris.payload.request.DepartmentRequest;
import com.wozu.hris.payload.response.MessageResponse;
import com.wozu.hris.repositories.DepartmentEmployeeRepository;
import com.wozu.hris.repositories.DepartmentRepository;
import com.wozu.hris.repositories.EmployeeRepository;
import com.wozu.hris.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    DepartmentService dService;
    @Autowired
    DepartmentRepository dRepo;
    @Autowired
    DepartmentEmployeeRepository dERepo;
    @Autowired
    EmployeeRepository eRepo;

    // View all departments
    @GetMapping("/all")
    public ResponseEntity<List<Department>> getAllDepartments(){
        try{
            List<Department> departments = new ArrayList<Department>();

            dRepo.findAll().forEach(departments::add);

            if(departments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(departments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Views specific department
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable("id") Long id) {
        Department department = dService.findDepartmentById(id);
        return ResponseEntity.ok(department);
    }

    // Create department
    @PostMapping("/create")
    public ResponseEntity<?> createDepartment(@Valid @RequestBody DepartmentRequest departmentRequest) {
        Department department = new Department();
        department.setName(departmentRequest.getName());
        department.setManager(departmentRequest.getManager());
        dService.createDepartment(department);
        if (!departmentRequest.getEmployees().isEmpty()) {
            for (int i = 0; i < departmentRequest.getEmployees().size(); i++) {
                DepartmentEmployee dE = new DepartmentEmployee(dRepo.findByName(department.getName()), departmentRequest.getEmployees().get(i));
                dERepo.save(dE);
            }
        }

        return ResponseEntity.ok(new MessageResponse("Department " + department.getName() + " created."));
    }

    // Update department
    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateDepartment(@Valid @RequestBody DepartmentRequest departmentRequest,
                                              @PathVariable("id") Long id){
        Department department = dRepo.findById(id).get();
        department.setName(departmentRequest.getName());
        department.setManager(departmentRequest.getManager());
        dService.updateDepartemnts(id, department);
        List<Employee> addList = new ArrayList<>(eRepo.findByDepartmentEmployeeDepartment(department));
        List<Employee> delList = new ArrayList<>(departmentRequest.getEmployees());
        addList.removeAll(departmentRequest.getEmployees());
        delList.removeAll(eRepo.findByDepartmentEmployeeDepartment(department));
        for (int i = 0; i < addList.size(); i++) {
            DepartmentEmployee dE = new DepartmentEmployee(dRepo.findByName(department.getName()), addList.get(i));
        }
        for (int i = 0; i < delList.size(); i++) {
            Optional<DepartmentEmployee> dE = dERepo.findByEmployeeAndDepartment(delList.get(i), department);
            dERepo.delete(dE.get());
        }
        return ResponseEntity.ok(new MessageResponse("Department successfully updated."));
    }

    // Delete department
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable("id") Long id) {
        dService.deleteDepartment(id);
        return ResponseEntity.ok(new MessageResponse("Department successfully deleted."));
    }
}