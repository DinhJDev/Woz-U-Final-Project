package com.wozu.hris.controllers;

import com.wozu.hris.models.Employee;
import com.wozu.hris.security.jwt.JwtUtils;
import com.wozu.hris.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class HomeController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    EmployeeService eService;

    @PreAuthorize("hasRole('CANDIDATE') or hasRole('EMPLOYEE') or hasRole('MANAGER') or hasRole('HR')")
    @GetMapping("/dashboard")
    public ResponseEntity<Employee> dashboard(@RequestHeader("Authorization") String token) {
        String username = jwtUtils.getUserNameFromJwtToken(token);
        Employee employee = eService.findByUsername(username);          // Utilizes JwtToken to obtain username & gets Employee

        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PreAuthorize("hasRole('HR')")
    @GetMapping("/adminboard")
    public ResponseEntity<Employee> adminboard(@RequestHeader("Authorization") String token) {
        String username = jwtUtils.getUserNameFromJwtToken(token);
        Employee employee = eService.findByUsername(username);          // Utilizes JwtToken to obtain username & gets Employee

        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
