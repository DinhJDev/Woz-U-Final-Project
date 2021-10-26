package com.wozu.hris.services;

import com.wozu.hris.models.Payrate;
import com.wozu.hris.repositories.PayratesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*

    -----------------------------------------------------------------------------------
                                    PAYRATE SERVICE
                                  created by Nathan Du
                                       10/20/2021
    -----------------------------------------------------------------------------------

 */

@Service
public class PayrateService {

    @Autowired
    PayratesRepository prRepo;

    public List<Payrate> allPayrates(){
        return prRepo.findAll();
    }

    public Payrate createPayrate(Payrate p){
        return prRepo.save(p);
    }

    public Payrate findPayrate(Long Id){
        Optional<Payrate> optionalPayrate = prRepo.findById(Id);
        if(optionalPayrate.isPresent()){
            return optionalPayrate.get();
        }else{
            return null;
        }
    }

    public Payrate updatePayrate(Long Id, Payrate payrate){
        Optional<Payrate> optionalPayrate = prRepo.findById(Id);
        if(optionalPayrate.isPresent()){
            return prRepo.save(payrate);
        }else{
            return null;
        }
    }

    public void deletePayrate(Long Id){
        this.prRepo.deleteById(Id);
    }

}
