package com.wozu.hris.models;

// positions model
//created: 10/25/21
//updated: 10/25/21 AF

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="positions")
public class Position {
    //attributes
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "position")
    private List<DepartmentEmployee> departmentEmployee;


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
}
