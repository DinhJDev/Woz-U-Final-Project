package com.wozu.hris.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

// departments model
//created: 10/25/21
//updated: 10/25/21 AF

@Entity
@Table(name="departments")
public class Department {

    //attributes
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    private Date createdAt;
    private Date updatedAt;

    //create
    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }

    //update
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

    //relationships
    @OneToOne
    @JoinColumn(name = "manager_id")
    @JsonIgnore
    private Employee manager;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<DepartmentEmployee> employees;

    //getters


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Employee getManager() {
        return manager;
    }

    public List<DepartmentEmployee> getEmployees() {
        return employees;
    }

    //setters


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreatedAt(Date c) {
        this.createdAt = c;
    }

    public void setUpdatedAt(Date u) {
        this.updatedAt = u;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public void setEmployees(List<DepartmentEmployee> employees) {
        this.employees = employees;
    }
}
