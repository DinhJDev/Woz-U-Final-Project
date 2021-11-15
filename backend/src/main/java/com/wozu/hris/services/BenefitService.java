package com.wozu.hris.services;

import com.wozu.hris.models.Benefit;
import com.wozu.hris.models.Employee;
import com.wozu.hris.repositories.BenefitRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
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
    @Autowired EmployeeService eService;

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
        Benefit b = benefitRepository.getById(benefitID);
        List<Employee> emps = b.getEmployees();
        emps.forEach((e)-> e.setBenefit(null));

        eService.updateEmployees(emps);

        benefitRepository.deleteById(benefitID);
    }

    /*----------------------------------------------------------------
    Custom Queries
    ----------------------------------------------------------------*/

    public Benefit findByName(String name){
        Optional<Benefit> optional = benefitRepository.findByName(name);
        if(optional.isPresent()){
            return optional.get();
        }else{
            return null;
        }
    }

    public Boolean existsByEmployee(Employee e){
        return e.getBenefit() != null ? true : false;
    }

    public List<Employee> findAllById(Long Id){
        return benefitRepository.findAllById(Id);
    }

    public Boolean existsByName(String name){
        return benefitRepository.existsByName(name);
    }

    public Boolean existsById(Long Id){
        return benefitRepository.existsById(Id);
    }

}
