package com.wozu.hris.repositories;

import com.wozu.hris.models.Payrates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*

    -----------------------------------------------------------------------------------
                                   PAYRATE REPOSITORY
                                  created by Nathan Du
                                       10/20/2021
    -----------------------------------------------------------------------------------

 */

@Repository
public interface payratesRepository extends JpaRepository<Payrates, Long> {
}
