package com.wozu.hris.controllers;

import com.wozu.hris.models.Payrate;
import com.wozu.hris.payload.response.MessageResponse;
import com.wozu.hris.repositories.PayrateRepository;
import com.wozu.hris.services.PayrateService;
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
@RequestMapping("/api/payrates")
public class PayratesController {
    @Autowired
    PayrateService payrateService;
    @Autowired
    PayrateRepository payrateRepository;

    // create payrate
    @PostMapping("/create")
    public ResponseEntity<?> createPayrate(@RequestBody Payrate payrate) {
        payrateService.createPayrate(payrate);
        return ResponseEntity.ok(new MessageResponse("Payrate for employee " + payrate.getEmployee().getId()));
    }

    // get all payrates rest api
    @PreAuthorize("hasRole('HR')")
    @GetMapping("/payrates")
    public ResponseEntity<List<Payrate>> getAllPayrates(){
        try{
            List<Payrate> payrates = new ArrayList<Payrate>();
            payrateRepository.findAll().forEach(payrates::add);
            if(payrates.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(payrates, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get payroll by id rest api
    @PreAuthorize("hasRole('HR')")
    @GetMapping("/payrates/{id}")
    public ResponseEntity<Payrate> getPayrateById(@PathVariable Long id){
        Payrate payrate = payrateService.findPayrate(id);
        return ResponseEntity.ok(payrate);
    }

    // delete payroll rest api
    @PreAuthorize("hasRole('HR')")
    @DeleteMapping("/payrates/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePayrate(@PathVariable Long id){
        Payrate payrate = payrateService.findPayrate(id);
        payrateService.deletePayrate(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return  ResponseEntity.ok(response);
    }

}
