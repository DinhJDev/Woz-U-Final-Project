package com.wozu.hris.services;

import com.wozu.hris.models.Benefit;
import com.wozu.hris.repositories.BenefitRepository;
import com.wozu.hris.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/* ------------------------------------------------------------------
**  —— Benefits Service
**  —— Created by: Chetachi Ezikeuzor
** -----------------------------------------------------------------*/

/* ------------------------------------------------------------------
**  —— TABLE OF CONTENTS
**  —— 25. Add Dependency
**  —— 35. Return benefits
**  —— 43. Create benefit
**  —— 51. Retrieve benefit
**  —— 63. Update benefit
**  —— 76. Delete benefit
** -----------------------------------------------------------------*/

@Service
public class BenefitService {

    /*----------------------------------------------------------------
    ADD BENEFIT REPOSITORY AS DEPENDENCY
    ----------------------------------------------------------------*/

    @Autowired BenefitRepository benefitRepository;
    @Autowired
    EmployeeRepository eRepo;

    /*----------------------------------------------------------------
    RETURN ALL BENEFITS
    ----------------------------------------------------------------*/

    public List<Benefit> allBenefits() {
        return benefitRepository.findAll();
    }

    /*----------------------------------------------------------------
    CREATE BENEFIT
    ----------------------------------------------------------------*/

    public Benefit createBenefit(Benefit benefitItem) {
        return benefitRepository.save(benefitItem);
    }

    /*----------------------------------------------------------------
    RETRIEVE BENEFIT
    ----------------------------------------------------------------*/

    public Benefit findBenefit(Long benefitID) {
        Optional<Benefit> optionalBenefit = benefitRepository.findById(benefitID);
        if(optionalBenefit.isPresent()) {
            return optionalBenefit.get();
        } else {
            return null;
        }
    }

    /*----------------------------------------------------------------
    UPDATE BENEFIT
    ----------------------------------------------------------------*/

    public Benefit updateBenefit(Long benefitID, Benefit benefit) {
        Optional<Benefit> optionalBenefit = benefitRepository.findById(benefitID);
        if(optionalBenefit.isPresent()) {
            return benefitRepository.save(benefit);
        } else {
            return null;
        }
    }

    /*----------------------------------------------------------------
    DELETE BENEFIT
    ----------------------------------------------------------------*/

    public void deleteBenefit(Long benefitID) {

        this.benefitRepository.deleteById(benefitID);
    }
}
