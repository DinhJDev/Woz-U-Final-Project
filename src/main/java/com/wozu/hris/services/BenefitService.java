package com.wozu.hris.services;

import com.wozu.hris.models.Benefit;
import com.wozu.hris.repositories.BenefitRepository;
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
** -----------------------------------------------------------------*/

@Service
public class BenefitService {

    /*----------------------------------------------------------------
    ADD BENEFIT REPOSITORY AS DEPENDENCY
    ----------------------------------------------------------------*/

    private final BenefitRepository benefitRepository;

    public BenefitService(BenefitRepository benefitRepository) {
        this.benefitRepository = benefitRepository;
    }

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
}
