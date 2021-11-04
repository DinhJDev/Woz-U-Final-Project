package com.wozu.hris.controllers;

import com.wozu.hris.models.Account;
import com.wozu.hris.models.Employee;
import com.wozu.hris.security.jwt.JwtUtils;
import com.wozu.hris.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class HomeController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AccountService aService;

    @PreAuthorize("hasRole('CANDIDATE') or hasRole('EMPLOYEE') or hasRole('HR')")
    @GetMapping("/dashboard")
    public ResponseEntity<Employee> dashboard(@RequestHeader("Authorization") String token) {
        String username = jwtUtils.getUserNameFromJwtToken(token);
        Optional<Account> account = aService.findByUsername(username);          // Utilizes JwtToken to obtain username & gets Employee

        if (account.isPresent()) {
            return new ResponseEntity<>(account.get().getEmployee(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
