package com.wozu.hris.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="employees_trainings")
public class EmployeeTraining {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateOfCompletion;
    @PrePersist
    protected void onCreate(){
        this.dateOfCompletion = new Date();
    }

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name="employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name="training_id")
    private Training training;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateOfCompletion() {
        return dateOfCompletion;
    }

    public void setDateOfCompletion(Date dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }
}
