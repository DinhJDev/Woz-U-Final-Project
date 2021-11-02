package com.wozu.hris.repositories;

import com.wozu.hris.models.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*

    -----------------------------------------------------------------------------------
                                   PAYROLL REPOSITORY
                                  created by Nathan Du
                                       10/20/2021
    -----------------------------------------------------------------------------------

 */

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {
}
