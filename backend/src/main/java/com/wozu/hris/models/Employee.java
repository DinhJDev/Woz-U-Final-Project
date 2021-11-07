package com.wozu.hris.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isClockedIn;
  
    @OneToOne(mappedBy = "employee")
    private Account account;

    @OneToMany(mappedBy = "employee")
    private List<Timesheet> timesheets;

    @OneToMany(mappedBy = "employee")
    private Set<EmployeeTraining> employeeTrainings = new HashSet<EmployeeTraining>();

    @OneToMany(mappedBy = "employee")
    private List<Payroll> payrolls;

    @OneToOne(mappedBy = "employee")
    private Payrate payrate;

    @ManyToOne
    @JoinColumn(name="benefit_id")
    private Benefit benefit;

    @OneToMany(mappedBy = "reviewer")
    private List<Performance> reviews;

    @OneToMany(mappedBy = "reviewee")
    private List<Performance> performances;

    @OneToOne(mappedBy = "manager")
    private Department mDepartment;

    @OneToMany(mappedBy = "employee")
    private List<DepartmentEmployee> department;

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

    public Employee() {
        this.firstName = "";
        this.lastName = "";
    }

    public Employee(String firstName, String lastName, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Timesheet> getTimesheets() {
        return timesheets;
    }

    public void setTimesheets(List<Timesheet> timesheets) {
        this.timesheets = timesheets;
    }

    public Set<EmployeeTraining> getEmployeeTrainings() {
        return employeeTrainings;
    }

    public void setEmployeeTrainings(Set<EmployeeTraining> employeeTrainings) {
        this.employeeTrainings = employeeTrainings;
    }

    public List<Payroll> getPayrolls() {
        return payrolls;
    }

    public void setPayrolls(List<Payroll> payrolls) {
        this.payrolls = payrolls;
    }

    public Payrate getPayrate() {
        return payrate;
    }

    public void setPayrate(Payrate payrate) {
        this.payrate = payrate;
    }

    public Benefit getBenefit() {
        return benefit;
    }

    public void setBenefit(Benefit benefit) {
        this.benefit = benefit;
    }

    public List<Performance> getReviews() {
        return reviews;
    }

    public void setReviews(List<Performance> reviews) {
        this.reviews = reviews;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public Department getmDepartment() {
        return mDepartment;
    }

    public void setmDepartment(Department mDepartment) {
        this.mDepartment = mDepartment;
    }

    public List<DepartmentEmployee> getDepartment() {
        return department;
    }

    public void setDepartment(List<DepartmentEmployee> department) {
        this.department = department;
    }

    public Employee (String firstName, String lastName, Date dateOfBirth, Benefit benefit){
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.benefit = benefit; //or whatever level employees should have by default
    }
    public Boolean getClockedIn() {
        return isClockedIn;
    }

    public void setClockedIn(Boolean clockedIn) {
        isClockedIn = clockedIn;
    }
}
