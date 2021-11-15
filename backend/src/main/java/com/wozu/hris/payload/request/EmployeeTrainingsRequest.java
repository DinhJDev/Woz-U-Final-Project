package com.wozu.hris.payload.request;

import com.wozu.hris.models.Training;

import java.util.List;

public class EmployeeTrainingsRequest {
    private Long id;
    List<Training> trainings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
    }
}
