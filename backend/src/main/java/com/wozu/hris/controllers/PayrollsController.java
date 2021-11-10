package com.wozu.hris.controllers;

import com.wozu.hris.models.*;
import com.wozu.hris.repositories.PayrollRepository;
import com.wozu.hris.services.PayrollService;
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
@RequestMapping("/api/payrolls")
public class PayrollsController {
    @Autowired
    PayrollService payrollService;
    @Autowired
    PayrollRepository payrollRepository;

    // get all payrolls rest api
    @PreAuthorize("hasRole('HR')")
    @GetMapping("/payrolls")
    public ResponseEntity<List<Payroll>> getAllPayrolls(){
        try{
            List<Payroll> payrolls = new ArrayList<Payroll>();
            payrollRepository.findAll().forEach(payrolls::add);
            if(payrolls.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(payrolls, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get payroll by id rest api
    @PreAuthorize("hasRole('HR')")
    @GetMapping("/payrolls/{id}")
    public ResponseEntity<Payroll> getPayrollById(@PathVariable Long id){
        Payroll payroll = payrollService.findPayroll(id);
        return ResponseEntity.ok(payroll);
    }

    // delete payroll rest api
    @PreAuthorize("hasRole('HR')")
    @DeleteMapping("/payrolls/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePayroll(@PathVariable Long id){
        Payroll payroll = payrollService.findPayroll(id);
        payrollService.deletePayroll(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return  ResponseEntity.ok(response);
    }

}
