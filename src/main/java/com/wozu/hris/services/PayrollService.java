package com.wozu.hris.services;

import com.wozu.hris.models.Payroll;
import com.wozu.hris.repositories.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*

    -----------------------------------------------------------------------------------
                                    PAYROLL Service
                                  created by Nathan Du
                                       10/20/2021
    -----------------------------------------------------------------------------------

 */

@Service
public class PayrollService {

    @Autowired
    PayrollRepository prRepo;

    public List<Payroll> allPayrolls(){
        return prRepo.findAll();
    }

    public Payroll createPayroll(Payroll p){
        return prRepo.save(p);
    }

    public Payroll findPayroll(Long Id){
        Optional<Payroll> optionalPayroll = prRepo.findById(Id);
        if(optionalPayroll.isPresent()){
            return optionalPayroll.get();
        }else{
            return null;
        }
    }

    public Payroll updatePayroll(Long Id, Payroll payroll){
        Optional<Payroll> optionalPayroll = prRepo.findById(Id);
        if(optionalPayroll.isPresent()){
            return prRepo.save(payroll);
        }else{
            return null;
        }
    }

    public void deletePayroll(Long Id){
        this.prRepo.deleteById(Id);
    }
}
