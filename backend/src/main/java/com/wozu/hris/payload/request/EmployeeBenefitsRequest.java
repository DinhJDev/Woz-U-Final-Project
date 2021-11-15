package com.wozu.hris.payload.request;

import com.wozu.hris.models.Benefit;

public class EmployeeBenefitsRequest {
    private Long id;
    private Benefit benefit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Benefit getBenefit() {
        return benefit;
    }

    public void setBenefit(Benefit benefit) {
        this.benefit = benefit;
    }
}
