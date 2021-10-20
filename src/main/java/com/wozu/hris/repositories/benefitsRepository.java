package com.wozu.hris.repositories;

import com.wozu.hris.models.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* ------------------------------------------------------------------
**  —— Benefits Repository
**  —— Created by: Chetachi Ezikeuzor
** -----------------------------------------------------------------*/

@Repository
public interface benefitsRepository extends JpaRepository<Benefit, Long> {

}
