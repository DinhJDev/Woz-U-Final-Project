package com.wozu.hris.controllers;

import com.wozu.hris.models.Benefit;
import com.wozu.hris.repositories.BenefitRepository;
import com.wozu.hris.services.BenefitService;
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
@RequestMapping("/api/benefits")
public class BenefitsController {

        @Autowired
        BenefitService benefitService;
        @Autowired
        BenefitRepository eRepo;

        // get all benefits
        @PreAuthorize("hasRole('HR') or hasRole('MANAGER')")
        @GetMapping("/benefits")
        public ResponseEntity<List<Benefit>> getAllBenefits(){
            try {
                List<Benefit> benefits = new ArrayList<Benefit>();

                eRepo.findAll().forEach(benefits::add);

                if(benefits.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }

                return new ResponseEntity<>(benefits, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // create benefit rest api
        @PostMapping("/benefits")
        public Benefit createBenefit(@RequestBody Benefit benefit){
            return benefitService.createBenefit(benefit);
        }

        // get benefit by id rest api
        @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER') or hasRole('HR')")
        @GetMapping("/benefits/{id}")
        public ResponseEntity<Benefit> getBenefitById(@PathVariable Long id){
            Benefit benefit = benefitService.findBenefit(id);
            return ResponseEntity.ok(benefit);
        }

        // update benefit rest api
        @PutMapping("/benefits/{id}")
        public ResponseEntity<Benefit> updateBenefit(@PathVariable Long id, @RequestBody Benefit benefitDetails){
            Benefit benefit = benefitService.findBenefit(id);

            benefit.setDescription(benefitDetails.getDescription());
            benefit.setName(benefitDetails.getName());


            Benefit updatedBenefit = benefitService.updateBenefit(id, benefit);

            return  ResponseEntity.ok(updatedBenefit);
        }

        // delete benefit rest api
        @DeleteMapping("/benefits/{id}")
        public ResponseEntity<Map<String, Boolean>> deleteBenefit(@PathVariable Long id){
            Benefit benefit = benefitService.findBenefit(id);
            benefitService.deleteBenefit(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return  ResponseEntity.ok(response);
        }
}
