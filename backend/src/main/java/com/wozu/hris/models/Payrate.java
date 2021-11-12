package com.wozu.hris.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
/*

    -----------------------------------------------------------------------------------
                                     PAYRATES MODEL
                                   created by Nathan Du
                                       10/19/2021
    -----------------------------------------------------------------------------------
                                     DOCUMENTATION
    -----------------------------------------------------------------------------------
                                           ~~
                   - contains distinct ID for each Payrate log.
                   - Stores Unique Employee ID via "employee_id"
                   - Returns/Stores hourlyRate as Double.
                   - Returns/Stores salary as Double.
                   - Returns/Stores effectiveDate as Date.
                   - Stored in MySQL as "payrates"
    -----------------------------------------------------------------------------------
                                        LINE INDEX
    -----------------------------------------------------------------------------------
                                 Attributes - 35
                     Created And Updated At - 54
                        Getters And Setters - 75
             Created And Updated At Getters - 123
*/
@Entity
@Table(name="payrates")
public class Payrate {
    /*

    -----------------------------------------------------------------------------------
                                        ATTRIBUTES
    -----------------------------------------------------------------------------------

     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double hourlyRate;
    private Double salary;
    private Date effectiveDate;

    /*

   -----------------------------------------------------------------------------------
                                       Constructor
   -----------------------------------------------------------------------------------

    */

    public Payrate(){}

    public Payrate(Employee employee){
        this.employee = employee;
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

    @OneToOne
    @JoinColumn(name = "employee_id")
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

    public void setEmployee(Employee employee){this.employee = employee;}

    public Employee getEmployee(){return this.employee;}

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
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
