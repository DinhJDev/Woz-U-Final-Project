package com.wozu.hris.controllers;

import com.wozu.hris.models.Timesheet;
import com.wozu.hris.services.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/timesheet")
public class TimesheetController {
    @Autowired
    TimesheetService tService;

    @PostMapping("/clockin")
    public Timesheet createTimesheet(){
        Timesheet t = new Timesheet();
        t.setStart(new Date());
        return tService.createTimesheet(t);
    }
}
