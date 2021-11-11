package com.wozu.hris.services;
import com.wozu.hris.models.Training;
import com.wozu.hris.repositories.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingService {
    @Autowired
    TrainingRepository trainingRepository;
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
        this.trainingRepository.deleteById(id);
    }

    public List<Training> findAllNotIn(List<String> t){
        return trainingRepository.findByTrainingNameNotIn(t);
    }

    public Training findByTrainingName(String n){
        Optional<Training> opt = trainingRepository.findByTrainingName(n);
        if(opt.isPresent()){
            return opt.get();
        }else{
            return null;
        }
    }
}