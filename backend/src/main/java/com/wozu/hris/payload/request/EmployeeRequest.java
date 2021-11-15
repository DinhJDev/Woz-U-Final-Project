package com.wozu.hris.payload.request;

import java.util.Date;

public class EmployeeRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
}
