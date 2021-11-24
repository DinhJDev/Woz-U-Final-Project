package com.wozu.hris.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


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
  
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Account account;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Timesheet> timesheets;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployeeTraining> employeeTrainings = new HashSet<>(); //

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Payroll> payrolls;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Payrate payrate;

    @ManyToOne
    @JoinColumn(name="benefit_id")
    private Benefit benefit; // x

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL)
    private List<Performance> reviews;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "reviewee", cascade = CascadeType.ALL)
    private List<Performance> performances; // x

    @OneToOne(mappedBy = "manager")
    @JsonIgnore
    private Department mDepartment;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DepartmentEmployee> department; // x

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
        this.isClockedIn = false;
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

    public String totalHours(){
        double total = 0;
        for (Timesheet t: timesheets){
            if(t.getEnd() != null) {
                long diffInMillies = Math.abs(t.getStart().getTime() - t.getEnd().getTime());
                total += TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS) / 60;
            }
        }
        total = Math.round(total * 100.0)/100.0;

        return String.valueOf(total);
    }

    public void setTimesheets(List<Timesheet> timesheets) {
        this.timesheets = timesheets;
    }

    public Set<EmployeeTraining> getEmployeeTrainings() {
        return employeeTrainings;
    }

    public String getEmployeeTrainingString(){
        StringBuilder trainingList = new StringBuilder();
        int count = 1;

        for (EmployeeTraining e: employeeTrainings){
            trainingList.append(e.getTraining().getTrainingName());
            if (count < (employeeTrainings.size())){
                trainingList.append(", ");
                count++;
            }
        }
        return trainingList.toString();
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

    public String getBenefitName(){
        if(benefit != null) {
            return benefit.getName();
        }else{
            return "N/A";
        }
    }

    public void setBenefit(Benefit benefit) {
        this.benefit = benefit;
    }

    public List<Performance> getReviews() {
        return reviews;
    }

    public String getlastPerformance(){
        if(performances.size() > 0) {
            return performances.get(performances.size() - 1).getComment();
        }else{
            return "N/A";
        }
    }

    public String getStatus(){
        if (isClockedIn == null){
            return "Clocked Out";
        } else if (isClockedIn){
            return "Clocked In";
        }else{
            return "Clocked Out";
        }
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

    public String getEmployeeDepartmentString(){
        StringBuilder departemntList = new StringBuilder();
        int count = 1;

        for (DepartmentEmployee d: department){
            if(d.getDepartment() != null) {
                departemntList.append(d.getDepartment().getName());
                if (count < (department.size() - 1)) {
                    departemntList.append(", ");
                }
            }
        }
        if(departemntList == null || departemntList.length() < 1){
            return "";
        }
        return departemntList.toString();
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

    public String getPosition(){
        return "Employee";
    }

    public Boolean getClockedIn() {
        if(isClockedIn == null){
            return false;
        }
        return isClockedIn;
    }

    public void setClockedIn(Boolean clockedIn) {
        isClockedIn = clockedIn;
    }
}
