package com.wozu.hris.services;

import com.wozu.hris.models.Employee;
import com.wozu.hris.models.Payroll;
import com.wozu.hris.models.Timesheet;
import com.wozu.hris.repositories.PayrollRepository;
import com.wozu.hris.repositories.TimesheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    TimesheetRepository tRepo;

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

    public Payroll calculatePayroll(Employee employee){
        Timesheet timesheet = tRepo.findTopByEmployeeOrderByIdDesc(employee);
        Payroll c_Payroll = new Payroll(employee);
        c_Payroll.setDate(timesheet.getEnd());
        Double daily;
        Date start = timesheet.getStart();
        Date end = timesheet.getEnd();
        long diffInMillies = Math.abs(start.getTime() - end.getTime());
        double diffMinutes = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
        daily = diffMinutes/60;
        Double amount = null;
        if(employee.getPayrate() != null) {
            amount = employee.getPayrate().getHourlyRate() * daily;
            amount = Math.round(amount * 100.0)/100.0;
        }
        c_Payroll.setAmount(amount);
        return prRepo.save(c_Payroll);
    }

    public void deletePayroll(Long Id){
        this.prRepo.deleteById(Id);
    }
}
