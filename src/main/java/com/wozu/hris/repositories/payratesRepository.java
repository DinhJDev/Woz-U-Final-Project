package com.wozu.hris.repositories;

import com.wozu.hris.models.Payrates;
import org.springframework.data.jpa.repository.JpaRepository;

/*

    -----------------------------------------------------------------------------------
                                   PAYRATE REPOSITORY
                                  created by Nathan Du
                                       10/20/2021
    -----------------------------------------------------------------------------------

 */


public interface payratesRepository extends JpaRepository<Payrates, Integer> {
}
