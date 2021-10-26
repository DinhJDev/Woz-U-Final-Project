package com.wozu.hris.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

// departments model
//created: 10/25/21
//updated: 10/25/21 AF

@Entity
@Table(name="departments")
public class Departments {

    //attributes
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long dept_id;
    @NotNull
    private String dept_name;
    @NotNull
    private int manager_id;
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


    //getters
    public Long getDeptId() {
        return dept_id;
    }

    public String getDeptName() {
        return dept_name;
    }

    public int getManagerId() {
        return manager_id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }


    //setters
    public void setDeptId(Long did) {
        this.dept_id = did;
    }

    public void setDeptName(String dn) {
        this.dept_name = dn;
    }

    public void setManagerId(int mid) {
        this.manager_id = mid;
    }

    public void setCreatedAt(Date c) {
        this.createdAt = c;
    }

    public void setUpdatedAt(Date u) {
        this.updatedAt = u;
    }
}
