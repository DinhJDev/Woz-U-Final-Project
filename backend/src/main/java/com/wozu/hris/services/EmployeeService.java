package com.wozu.hris.services;

import com.wozu.hris.models.Department;
import com.wozu.hris.models.DepartmentEmployee;
import com.wozu.hris.models.Employee;
import com.wozu.hris.models.EmployeeTraining;
import com.wozu.hris.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {
    // Adding the employee repository as a dependency
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DepartmentEmployeeService deService;
    @Autowired
    EmployeeTrainingService etService;

    // Returns all the employees
    public List<Employee> allEmployees() {
        return employeeRepository.findAll();
    }

    public Employee findByUsername(String username) {
        Optional<Employee> optionalEmployee = employeeRepository.findByAccountUsernameIgnoreCase(username);
        if(optionalEmployee.isPresent()) {
            return optionalEmployee.get();
        } else {
            return null;
        }
    }

    // Creates a employee
    public Employee createEmployee(Employee e) {
        return employeeRepository.save(e);
    }
    // Reads a employee by id
    public Employee findEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent()) {
            return optionalEmployee.get();
        } else {
            return null;
        }
    }
    // Updates an employee
    public Employee updateEmployee(Long id, Employee employee) {
        Optional<Employee> optionalE = employeeRepository.findById(id);
        if(optionalE.isPresent()) {
            return employeeRepository.save(employee);
        } else {
            return null;
        }
    }
    // Delete an employee
    public void deleteEmployee(Long id) {
        Employee E = employeeRepository.getById(id);

        List<DepartmentEmployee> de = E.getDepartment();
        de.forEach((e)-> e.setDepartment(null));
        deService.saveAll(de);

        Set<EmployeeTraining> et = E.getEmployeeTrainings();
        et.forEach((e)->e.setTraining(null));
        etService.saveAll(et);

        employeeRepository.deleteById(id);
    }

    public Boolean existsById(Long Id){
        return employeeRepository.existsById(Id);
    }

    public void updateEmployees(List<Employee> e){
        employeeRepository.saveAll(e);
    }
}

