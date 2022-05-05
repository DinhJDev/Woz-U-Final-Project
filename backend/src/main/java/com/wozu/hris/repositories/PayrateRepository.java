package com.wozu.hris.repositories;

import com.wozu.hris.models.Employee;
import com.wozu.hris.models.Payrate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*

    -----------------------------------------------------------------------------------
                                   PAYRATE REPOSITORY
                                  created by Nathan Du
                                       10/20/2021
    -----------------------------------------------------------------------------------

 */

@Repository
public interface PayrateRepository extends JpaRepository<Payrate, Long> {

    Boolean existsByEmployee(Employee employee);

    Optional<Payrate> findByEmployee(Employee employee);
}
