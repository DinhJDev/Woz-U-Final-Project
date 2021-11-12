package com.wozu.hris.repositories;

import com.wozu.hris.models.Benefit;
import com.wozu.hris.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/* ------------------------------------------------------------------
**  —— Benefits Repository
**  —— Created by: Chetachi Ezikeuzor
** -----------------------------------------------------------------*/

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Long> {

    Optional<Benefit> findByName(String name);

    List<Employee> findAllById(Long id);

    Boolean existsByName(String name);

}

