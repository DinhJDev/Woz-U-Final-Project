package com.wozu.hris.controllers;

import com.wozu.hris.models.Account;
import com.wozu.hris.models.Employee;
import com.wozu.hris.models.Timesheet;
import com.wozu.hris.payload.response.MessageResponse;
import com.wozu.hris.repositories.TimesheetRepository;
import com.wozu.hris.security.jwt.JwtUtils;
import com.wozu.hris.services.AccountService;
import com.wozu.hris.services.EmployeeService;
import com.wozu.hris.services.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
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
    @Autowired
    EmployeeService eService;

    // Displays last 3 timesheets
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER') or hasRole('HR')")
    @GetMapping("/")
    public List<Timesheet> timesheetList(@RequestHeader("Authorization") String token) {
        String username = jwtUtils.getUserNameFromJwtToken(token);
        Optional<Account> account = aService.findByUsername(username);          // Utilizes JwtToken to obtain username & gets Employee
        Employee employee = account.get().getEmployee();

        return tRepo.findTop3ByEmployee(employee);
    }

    @PreAuthorize("hasRole('HR') or hasRole('MANAGER')")
    @GetMapping("/{id}")
    public List<Timesheet> employeeTimesheetList(@PathVariable("id") Long id) {
        return tRepo.findAllByEmployeeId(id);
    }

    // Clock-In Request
    @PostAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER') or hasRole('HR')")
    @PostMapping("/clockin")
    public ResponseEntity<?> clockIn(@RequestHeader("Authorization") String token) {
        String username = jwtUtils.getUserNameFromJwtToken(token);
        Optional<Account> account = aService.findByUsername(username);          // Utilizes JwtToken to obtain username & gets Employee
        Employee employee = account.get().getEmployee();

        if (!employee.getClockedIn() == true) {
            Timesheet timesheet = new Timesheet();                                  // Initializes Timesheet Object

            timesheet.setStart(new Date());
            timesheet.setEmployee(employee);
            tService.createTimesheet(timesheet);                                    // Updates Attributes & Saves
            employee.setClockedIn(true);                                            // Sets isClockedIn to true
            eService.updateEmployee(employee.getId(), employee);
            return ResponseEntity.ok(new MessageResponse("User successfully clocked in at " + timesheet.getStart()));
        } else {
            return ResponseEntity.ok(new MessageResponse("Clock out before you can clock in."));
        }
    }


    // Clock-Out Request
    @PostAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER') or hasRole('HR')")
    @PostMapping("/clockout")
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
        tService.updateTimesheet(latestTimesheet.getId(), latestTimesheet);
        employee.setClockedIn(false);
        eService.updateEmployee(employee.getId(), employee);// Sets isClockedIn to false
        return ResponseEntity.ok(new MessageResponse("User successfully clocked out at " + latestTimesheet.getEnd()));
    }
}
