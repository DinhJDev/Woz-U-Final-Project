package com.wozu.hris.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "departments_employees")
public class DepartmentEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="position_id")
    private Position position;

    @ManyToOne
    @JoinColumn(name="department_id")
    private Department department;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="employee_id")
    private Employee employee;

    public DepartmentEmployee(){

    }

    public DepartmentEmployee(Department department, Employee employee) {
        this.department = department;
        this.employee = employee;
        this.position = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
