package com.wozu.hris.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

/*

    -----------------------------------------------------------------------------------
                                     TIMESHEET MODEL
                                   created by Nathan Du
                                       10/19/2021
    -----------------------------------------------------------------------------------
                                     DOCUMENTATION
    -----------------------------------------------------------------------------------
                                           ~~
                   - contains distinct ID for each Timesheet log.
                   - Stores Unique Employee ID via "employee_id"
                   - Returns/Stores date as Date
                   - Returns/Stores time_start as Time
                   - Returns/Stores time_end as Time
                   - Stored in MySQL as "timesheet"
    -----------------------------------------------------------------------------------
                                        LINE INDEX
    -----------------------------------------------------------------------------------
                                 Attributes - 38
                     Created And Updated At - 57
                        Getters And Setters - 78
             Created And Updated At Getters - 127
*/

@Entity
@Table(name="timesheets")
public class Timesheet {
    /*

    -----------------------------------------------------------------------------------
                                        ATTRIBUTES
    -----------------------------------------------------------------------------------

     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date start;
    private Date end;

    public Timesheet(){}

    public Timesheet(Employee e){
        this.employee = e;
        this.start = new Date();
        this.end = null;
    }

    /*

    -----------------------------------------------------------------------------------
                                CREATED AND UPDATED AT
    -----------------------------------------------------------------------------------

     */

    private Date createdAt;
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

    /*

    -----------------------------------------------------------------------------------
                                      RELATIONSHIPS
    -----------------------------------------------------------------------------------

    */

    @ManyToOne
    @JoinColumn(name="employee_id")
    @JsonIgnore
    private Employee employee;

    /*

    -----------------------------------------------------------------------------------
                                   GETTERS AND SETTERS
    -----------------------------------------------------------------------------------

     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /*

    -----------------------------------------------------------------------------------
                                GETTERS FOR CREATED AND UPDATED AT
    -----------------------------------------------------------------------------------

     */


    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
