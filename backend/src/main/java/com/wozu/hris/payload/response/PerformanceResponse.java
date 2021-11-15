package com.wozu.hris.payload.response;

import java.util.Date;

public class PerformanceResponse {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String comment;
    private Long reviewerId;
    private Long revieweeId;

    public PerformanceResponse(Long id, Date createdAt, Date updatedAt, String comment,
                               Long reviewerId, Long revieweeId) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.comment = comment;
        this.reviewerId = reviewerId;
        this.revieweeId = revieweeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public Long getRevieweeId() {
        return revieweeId;
    }

    public void setRevieweeId(Long revieweeId) {
        this.revieweeId = revieweeId;
    }
}
