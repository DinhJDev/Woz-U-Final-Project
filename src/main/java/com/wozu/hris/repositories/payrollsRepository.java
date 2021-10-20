package com.wozu.hris.repositories;

import com.wozu.hris.models.Payrolls;
import org.springframework.data.jpa.repository.JpaRepository;

/*

    -----------------------------------------------------------------------------------
                                   PAYROLL REPOSITORY
                                  created by Nathan Du
                                       10/20/2021
    -----------------------------------------------------------------------------------

 */

public interface payrollsRepository extends JpaRepository<Payrolls, Integer> {
}
