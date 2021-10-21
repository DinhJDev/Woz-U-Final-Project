package com.wozu.hris.repositories;

import com.wozu.hris.models.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*

    -----------------------------------------------------------------------------------
                                  TIMESHEET REPOSITORY
                                  created by Nathan Du
                                       10/20/2021
    -----------------------------------------------------------------------------------

 */

@Repository
public interface timesheetRepository extends JpaRepository<Timesheet, Long> {
}
