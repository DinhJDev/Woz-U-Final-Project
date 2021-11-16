package com.wozu.hris.payload.request;

import com.wozu.hris.models.Department;

import java.util.List;

public class EmployeeDepartmentRequest {
    private Long id;
    private List<Department> department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Department> getDepartment() {
        return department;
    }

    public void setDepartment(List<Department> department) {
        this.department = department;
    }
}
