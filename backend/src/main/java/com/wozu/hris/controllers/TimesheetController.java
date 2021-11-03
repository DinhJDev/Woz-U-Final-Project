package com.wozu.hris.controllers;

import com.wozu.hris.models.Account;
import com.wozu.hris.models.Employee;
import com.wozu.hris.models.Timesheet;
import com.wozu.hris.payload.response.MessageResponse;
import com.wozu.hris.repositories.AccountRepository;
import com.wozu.hris.repositories.TimesheetRepository;
import com.wozu.hris.security.jwt.JwtUtils;
import com.wozu.hris.services.AccountService;
import com.wozu.hris.services.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/timesheet")
public class TimesheetController {
    @Autowired
    TimesheetService tService;
    @Autowired
    TimesheetRepository tRepo;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AccountService aService;

    @GetMapping("/")
    public List<Timesheet> timesheetList(@RequestHeader("Authorization") String token) {
        String username = jwtUtils.getUserNameFromJwtToken(token);
        Optional<Account> account = aService.findByUsername(username);          // Utilizes JwtToken to obtain username & gets Employee
        Employee employee = account.get().getEmployee();

        return tRepo.findTop3ByEmployee(employee);
    }

    @PostMapping("/clockin")
    public ResponseEntity<?> clockIn(@RequestHeader("Authorization") String token) {
        Timesheet timesheet = new Timesheet();                                  // Initializes Timesheet Object

        String username = jwtUtils.getUserNameFromJwtToken(token);
        Optional<Account> account = aService.findByUsername(username);          // Utilizes JwtToken to obtain username & gets Employee
        Employee employee = account.get().getEmployee();

        timesheet.setStart(new Date());
        timesheet.setEmployee(employee);
        tService.createTimesheet(timesheet);                                    // Updates Attributes & Saves
        employee.setClockedIn(true);                                            // Sets isClockedIn to true
        return ResponseEntity.ok(new MessageResponse("User successfully clocked in at " + timesheet.getStart()));
    }
    @PutMapping("/clockout")
    public ResponseEntity<?> clockoutTimesheet(@RequestHeader("Authorization") String token) {
        String username = jwtUtils.getUserNameFromJwtToken(token);
        Optional<Account> account = aService.findByUsername(username);          // Utilizes JwtToken to obtain username & gets Employee
        Employee employee = account.get().getEmployee();

        if (!employee.getClockedIn() == true) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User has not clocked in."));
        }

        Timesheet latestTimesheet = tRepo.findTopByEmployeeOrderByIdDesc(employee);     // Grabs the latest timesheet, updates, and saves it
        latestTimesheet.setEnd(new Date());
        employee.setClockedIn(false);                                                   // Sets isClockedIn to false
        return ResponseEntity.ok(new MessageResponse("User successfully clocked out at " + latestTimesheet.getEnd()));
    }
}
