package com.wozu.hris.services;

import com.wozu.hris.models.Employee;
import com.wozu.hris.models.EmployeeTraining;
import com.wozu.hris.models.Training;
import com.wozu.hris.repositories.EmployeeTrainingRepository;
import com.wozu.hris.repositories.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeTrainingService {

    @Autowired
    EmployeeTrainingRepository eTRepo;

    @Autowired
    TrainingRepository tRepo;

    public EmployeeTraining createEmployeeTraining(EmployeeTraining et){
        return eTRepo.save(et);
    }

    public EmployeeTraining updateEmployeeTraining(Long Id, EmployeeTraining et){
        Optional<EmployeeTraining> opt = eTRepo.findById(Id);
        if(opt.isPresent()){
            return eTRepo.save(et);
        }else{
            return null;
        }
    }

    public void deleteEmployeeTraining(Long Id){
        EmployeeTraining et = eTRepo.findById(Id).get();
        et.setEmployee(null);
        et.setTraining(null);
        eTRepo.save(et);

        eTRepo.deleteById(Id);
    }


    public EmployeeTraining findByEmployee(Employee e){
        Optional<EmployeeTraining> opt = eTRepo.findByEmployee(e);
        if(opt.isPresent()){
            return opt.get();
        }else{
            return null;
        }
    }

    public Boolean existsByEmployeeAndTraining(Employee e, Training t){
        return eTRepo.existsByEmployeeAndTraining(e, t);
    }

    public EmployeeTraining findByEmployeeAndTraining(Employee e, Training t){
        Optional<EmployeeTraining> opt = eTRepo.findByEmployeeAndTraining(e, t);
        if(opt.isPresent()){
            return opt.get();
        }else{
            return null;
        }

    }

    public List<EmployeeTraining> findAllByEmployee(Employee e){
        return eTRepo.findAllByEmployee(e);
    }

    public List<EmployeeTraining> findAllByTraining(Training t){
        return eTRepo.findAllByTraining(t);
    }

    public Boolean existsByEmployee(Employee e){
        return eTRepo.existsByEmployee(e);
    }

    public void saveAll(Set<EmployeeTraining> e){
        eTRepo.saveAll(e);
    }

    public void deleteAll(Training t){
        List<EmployeeTraining> et = eTRepo.findAllByTraining(t);
       eTRepo.deleteAllInBatch(et);
    }
}
