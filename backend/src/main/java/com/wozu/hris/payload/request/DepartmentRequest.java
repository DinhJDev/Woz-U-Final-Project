package com.wozu.hris.payload.request;

import com.wozu.hris.models.Employee;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class DepartmentRequest {
    @NotBlank
    private String name;
    private List<Employee> employees;
    private Employee manager;

    public String getName() {
        return name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Employee getManager() {
        return manager;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }
}
