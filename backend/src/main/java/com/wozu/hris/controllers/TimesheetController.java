package com.wozu.hris.controllers;

import com.wozu.hris.models.Account;
import com.wozu.hris.models.Employee;
import com.wozu.hris.models.Timesheet;
import com.wozu.hris.payload.response.MessageResponse;
import com.wozu.hris.repositories.AccountRepository;
import com.wozu.hris.security.jwt.JwtUtils;
import com.wozu.hris.services.AccountService;
import com.wozu.hris.services.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/timesheet")
public class TimesheetController {
    @Autowired
    TimesheetService tService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AccountService aService;

    @PostMapping("/clockin")
    public ResponseEntity<?> clockIn(@RequestHeader("Authorization") String token) {
        Timesheet timesheet = new Timesheet();

        String username = jwtUtils.getUserNameFromJwtToken(token);
        Optional<Account> account = aService.findByUsername(username);
        Employee employee = account.get().getEmployee();

        timesheet.setStart(new Date());
        timesheet.setEmployee(employee);
        tService.createTimesheet(timesheet);

        return ResponseEntity.ok(new MessageResponse("User successfully clocked in at " + timesheet.getStart()));
    }
    /*@PutMapping("/clockout")
    public Timesheet clockoutTimesheet(){

    }*/
}
