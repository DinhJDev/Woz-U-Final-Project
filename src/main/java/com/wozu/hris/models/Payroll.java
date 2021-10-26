package com.wozu.hris.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/*

    -----------------------------------------------------------------------------------
                                     PAYROLLS MODEL
                                   created by Nathan Du
                                       10/19/2021
    -----------------------------------------------------------------------------------
                                     DOCUMENTATION
    -----------------------------------------------------------------------------------
                                           ~~
                   - contains distinct ID for each Payroll log.
                   - Stores Unique Employee ID via "employee_id"
                   - Returns/Stores date as Date.
                   - Returns/Stores amount as Double.
                   - Stored in MySQL as "payrolls"
    -----------------------------------------------------------------------------------
                                        LINE INDEX
    -----------------------------------------------------------------------------------
                                 Attributes - 36
                     Created And Updated At - 54
                        Getters And Setters - 75
             Created And Updated At Getters - 115
*/

@Entity
@Table(name="payrolls")
public class Payroll {
    /*

    -----------------------------------------------------------------------------------
                                        ATTRIBUTES
    -----------------------------------------------------------------------------------

     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long employee_id;

    private Date date;
    private Double amount;

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
                                   GETTERS AND SETTERS
    -----------------------------------------------------------------------------------

     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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
