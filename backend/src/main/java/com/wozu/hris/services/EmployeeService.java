package com.wozu.hris.services;

import com.wozu.hris.models.Employee;
import com.wozu.hris.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    // Adding the employee repository as a dependency
    @Autowired
    EmployeeRepository employeeRepository;

    // Returns all the employees
    public List<Employee> allEmployees() {
        return employeeRepository.findAll();
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
        this.employeeRepository.deleteById(id);
    }
}

