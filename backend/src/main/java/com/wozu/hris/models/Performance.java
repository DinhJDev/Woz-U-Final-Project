package com.wozu.hris.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="performances")
public class Performance {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    @NotNull
    private String comment;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="reviewer_id")
    private Employee reviewer;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="reviewee_id")
    private Employee reviewee;

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

    public Performance(){

    }

    public Performance(String comment, Employee reviewer, Employee reviewee) {
        this.comment = comment;
        this.reviewer = reviewer;
        this.reviewee = reviewee;
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

    public Employee getReviewer() {
        return reviewer;
    }

    public void setReviewer(Employee reviewer) {
        this.reviewer = reviewer;
    }

    public Employee getReviewee() {
        return reviewee;
    }

    public void setReviewee(Employee reviewee) {
        this.reviewee = reviewee;
    }
}
