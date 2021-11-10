package com.wozu.hris.repositories;

import com.wozu.hris.models.Employee;
import com.wozu.hris.models.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/*

    -----------------------------------------------------------------------------------
                                   PAYROLL REPOSITORY
                                  created by Nathan Du
                                       10/20/2021
    -----------------------------------------------------------------------------------

 */

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {

    List<Payroll> findAllByDateBetweenAndEmployeeOrderByIdAsc(Date start, Date end, Employee employee);

}
