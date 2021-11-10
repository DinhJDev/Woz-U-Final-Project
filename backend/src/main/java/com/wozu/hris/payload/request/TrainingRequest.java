package com.wozu.hris.payload.request;

import javax.validation.constraints.NotBlank;

public class TrainingRequest {
    @NotBlank
    private String trainingName;
    private String description;

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
