package com.wozu.hris.payload.request;

import com.wozu.hris.models.Employee;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PerformanceRequest {
    @NotBlank
    private String comment;
    @NotNull
    private Long reviewee;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getReviewee() {
        return reviewee;
    }

    public void setReviewee(Long reviewee) {
        this.reviewee = reviewee;
    }
}
