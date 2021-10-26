package com.wozu.hris.models;

// positions model
//created: 10/25/21
//updated: 10/25/21 AF

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="positions")
public class Positions {
    //attributes
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long pos_id;
    @NotNull
    private String pos_name;
    @NotNull
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
    public Long getPosId() {
        return pos_id;
    }

    public String getPosName() {
        return pos_name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }


    //setters
    public void setPosId(Long pid) {
        this.pos_id = pid;
    }

    public void setPosName(String pn) {
        this.pos_name = pn;
    }

    public void setCreatedAt(Date c) {
        this.createdAt = c;
    }

    public void setUpdatedAt(Date u) {
        this.updatedAt = u;
    }
}
