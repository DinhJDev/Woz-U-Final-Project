package com.wozu.hris.controllers;

import com.wozu.hris.models.Benefit;
import com.wozu.hris.models.ERole;
import com.wozu.hris.models.Employee;
import com.wozu.hris.models.Performance;
import com.wozu.hris.payload.request.PerformanceRequest;
import com.wozu.hris.repositories.PerformanceRepository;
import com.wozu.hris.repositories.RoleRepository;
import com.wozu.hris.security.jwt.JwtUtils;
import com.wozu.hris.services.EmployeeService;
import com.wozu.hris.services.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api/performance")
public class PerformanceController {
    @Autowired
    PerformanceService pService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    EmployeeService eService;
    @Autowired
    RoleRepository rRepo;
    @Autowired
    PerformanceRepository pRepo;

    // get all performances
    @PreAuthorize("hasRole('HR') or hasRole('MANAGER')")
    @GetMapping("/performances")
    public ResponseEntity<List<Performance>> getAllPerformances(){
        try {
            List<Performance> performances = new ArrayList<Performance>();

            pRepo.findAll().forEach(performances::add);

            if(performances.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(performances, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get employee's performance reviews By id
    @GetMapping("/employee/{id}")
    public ResponseEntity<List<Performance>> getEmployeePerformances(@PathVariable("id") Long employeeId,
                                                                     @RequestHeader("Authorization") String token){
        String username = jwtUtils.getUserNameFromJwtToken(token);
        Employee employee = eService.findByUsername(username);

        if (employee == eService.findEmployee(employeeId) ||
                employee.getAccount().getRoles().contains(rRepo.findByName(ERole.ROLE_MANAGER)) ||
                employee.getAccount().getRoles().contains(rRepo.findByName(ERole.ROLE_HR))) {
            try {
                List<Performance> performances = pService.allEmployeePerformance(employeeId);

                if (performances.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }

                return new ResponseEntity<>(performances, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    // Get specific performance review
    @GetMapping("/{id}")
    public ResponseEntity<Performance> getPerformance(@PathVariable("id") Long id,
                                                      @RequestHeader("Authorization") String token){
        Performance performanceData = pService.findPerformance(id);

        String username = jwtUtils.getUserNameFromJwtToken(token);
        Employee employee = eService.findByUsername(username);

        if (performanceData != null) {
            if (employee == performanceData.getReviewee() ||
                    employee.getAccount().getRoles().contains(rRepo.findByName(ERole.ROLE_MANAGER)) ||
                    employee.getAccount().getRoles().contains(rRepo.findByName(ERole.ROLE_HR))
            ) {
                return new ResponseEntity<>(performanceData, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // Post manager/HR's review of employee
    @PreAuthorize("hasRole('MANAGER') or hasRole('HR')")
    @PostMapping("/create")
    public ResponseEntity<Performance> createPerformance(@RequestBody PerformanceRequest performanceRequest,
                                                         @RequestHeader("Authorization") String token) {
        String username = jwtUtils.getUserNameFromJwtToken(token);
        Employee employee = eService.findByUsername(username);

        try {
            Performance performance = new Performance(performanceRequest.getComment(), performanceRequest.getReviewee(), employee);
            Performance _performance = pService.createPerformance(performance);
            return new ResponseEntity<>(_performance, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update Performance Review
    @PreAuthorize("hasRole('MANAGER') or hasRole('HR')")
    @PutMapping("/{id}")
    public ResponseEntity<Performance> updatePerformance(@PathVariable("id") Long id,
                                                         @RequestBody PerformanceRequest performanceRequest,
                                                         @RequestHeader("Authorization") String token) {
        String username = jwtUtils.getUserNameFromJwtToken(token);
        Employee employee = eService.findByUsername(username);

        Performance performanceData = pService.findPerformance(id);

        if (performanceData != null) {
            performanceData.setReviewer(employee);
            performanceData.setComment(performanceRequest.getComment());
            return new ResponseEntity<>(pService.updatePerformance(id, performanceData), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete Performance Review
    @PreAuthorize("hasRole('MANAGER') or hasRole('HR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePerformance(@PathVariable("id") Long id) {
        try {
            pService.deletePerformance(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
