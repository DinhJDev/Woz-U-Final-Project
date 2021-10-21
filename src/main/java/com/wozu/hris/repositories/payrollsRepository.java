package com.wozu.hris.repositories;

import com.wozu.hris.models.Payrolls;
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
public interface payrollsRepository extends JpaRepository<Payrolls, Long> {
}
