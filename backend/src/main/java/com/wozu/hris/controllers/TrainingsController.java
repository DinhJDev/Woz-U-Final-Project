package com.wozu.hris.controllers;

import com.wozu.hris.models.Benefit;
import com.wozu.hris.models.Training;
import com.wozu.hris.repositories.TrainingRepository;
import com.wozu.hris.services.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api/trainings")
public class TrainingsController {
    @Autowired
    TrainingService trainingService;
    @Autowired
    TrainingRepository trainingRepository;

    // get all trainings rest api
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER') or hasRole('HR')")
    @GetMapping("/trainings")
    public ResponseEntity<List<Training>> getAllTrainings(){
        try{
            List<Training> trainings = new ArrayList<Training>();
            trainingRepository.findAll().forEach(trainings::add);
            if(trainings.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(trainings, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // create training rest api
    @PostMapping("/trainings")
    public Training createTraining(@RequestBody Training training){
        return trainingService.createTraining(training);
    }

    // get training by id rest api
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER') or hasRole('HR')")
    @GetMapping("/trainings/{id}")
    public ResponseEntity<Training> getTrainingById(@PathVariable Long id){
        Training training = trainingService.findTraining(id);
        return ResponseEntity.ok(training);
    }

    // update training rest api
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER') or hasRole('HR')")
    @PutMapping("/trainings/{id}")
    public ResponseEntity<Training> updateTraining(@PathVariable Long id, @RequestBody Training trainingDetails){
        Training training = trainingService.findTraining(id);

        training.setDescription(trainingDetails.getDescription());
        training.setTrainingName(trainingDetails.getTrainingName());

        Training updatedTraining = trainingService.updateTraining(id, training);

        return  ResponseEntity.ok(updatedTraining);
    }

    // delete training rest api
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER') or hasRole('HR')")
    @DeleteMapping("/trainings/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteTraining(@PathVariable Long id){
        Training training = trainingService.findTraining(id);
        trainingService.deleteTraining(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return  ResponseEntity.ok(response);
    }

}
