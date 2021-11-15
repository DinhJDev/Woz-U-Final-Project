package com.wozu.hris.services;
import com.wozu.hris.models.EmployeeTraining;
import com.wozu.hris.models.Training;
import com.wozu.hris.repositories.EmployeeTrainingRepository;
import com.wozu.hris.repositories.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingService {
    @Autowired
    TrainingRepository trainingRepository;
    @Autowired
    EmployeeTrainingRepository employeeTRepo;
    public List<Training> allTrainings() {
        return trainingRepository.findAll();
    }
    public Training createTraining(Training e) {
        return trainingRepository.save(e);
    }
    public Training findTraining(Long id) {
        Optional<Training> optionalTraining = trainingRepository.findById(id);
        if(optionalTraining.isPresent()) {
            return optionalTraining.get();
        } else {
            return null;
        }
    }
    public Training updateTraining(Long id, Training training) {
        Optional<Training> optionalE = trainingRepository.findById(id);
        if(optionalE.isPresent()) {
            return trainingRepository.save(training);
        } else {
            return null;
        }
    }
    public void deleteTraining(Long id) {
        List<EmployeeTraining> eT = employeeTRepo.findAllByTrainingId(id);
        employeeTRepo.deleteAllInBatch(eT);
        this.trainingRepository.deleteById(id);
    }
}