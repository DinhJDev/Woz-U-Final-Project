package com.wozu.hris.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/* ------------------------------------------------------------------
**  —— Benefits Model
**  —— Created by: Chetachi Ezikeuzor
** -----------------------------------------------------------------*/

/* ------------------------------------------------------------------
**  —— TABLE OF CONTENTS
**  —— 24. Attributes
**  —— 41. Create/Update
**  —— 54. Getters
**  —— 78. Setters
** -----------------------------------------------------------------*/

@Entity
@Table(name="benefits")
public class Benefit {

    /*----------------------------------------------------------------
    ATTRIBUTES
    ----------------------------------------------------------------*/

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    private Date createdAt;
    private Date updatedAt;

    /*----------------------------------------------------------------
    CREATE AND UPDATE
    ----------------------------------------------------------------*/

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

    /*----------------------------------------------------------------
    RELATIONSHIPS
    ----------------------------------------------------------------*/

    @OneToMany(mappedBy = "benefit")
    private List<Employee> employees;

    public Benefit(){}

    public Benefit(String name, String description){
        this.name = name;
        this.description = description;
    }

    /*----------------------------------------------------------------
    GETTERS
    ----------------------------------------------------------------*/

    public Long getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public Date getCreatedAt(){
        return createdAt;
    }

    public Date getUpdatedAt(){
        return updatedAt;
    }

    public List<Employee> getEmployees(){return employees;}

    /*----------------------------------------------------------------
    SETTERS
    ----------------------------------------------------------------*/

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}