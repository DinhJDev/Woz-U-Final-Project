package com.wozu.hris.services;

import com.wozu.hris.models.Timesheet;
import com.wozu.hris.repositories.TimesheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*

    -----------------------------------------------------------------------------------
                                   TIMESHEET SERVICE
                                  created by Nathan Du
                                       10/20/2021
    -----------------------------------------------------------------------------------

 */

@Service
public class TimesheetService {
    @Autowired
    TimesheetRepository tsRepo;

    public List<Timesheet> allTimesheets(){
        return tsRepo.findAll();
    }

    public Timesheet createTimesheet(Timesheet t){
        return tsRepo.save(t);
    }

    public Timesheet findTimesheet(Long Id){
        Optional<Timesheet> optionalTimesheet = tsRepo.findById(Id);
        if(optionalTimesheet.isPresent()){
            return optionalTimesheet.get();
        }else{
            return null;
        }
    }

    public Timesheet updateTimesheet(Long Id, Timesheet timesheet){
        Optional<Timesheet> optionalTimesheet = tsRepo.findById(Id);
        if(optionalTimesheet.isPresent()){
            return tsRepo.save(timesheet);
        }else{
            return null;
        }
    }

    public void deleteTimesheet(Long Id){
        this.tsRepo.deleteById(Id);
    }
}
