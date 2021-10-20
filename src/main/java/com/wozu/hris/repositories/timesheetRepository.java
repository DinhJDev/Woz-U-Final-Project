package com.wozu.hris.repositories;

import com.wozu.hris.models.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;

/*

    -----------------------------------------------------------------------------------
                                  TIMESHEET REPOSITORY
                                  created by Nathan Du
                                       10/20/2021
    -----------------------------------------------------------------------------------

 */

public interface timesheetRepository extends JpaRepository<Timesheet, Integer> {
}
