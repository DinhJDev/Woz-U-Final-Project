package com.wozu.hris.repositories;

import com.wozu.hris.models.Employee;
import com.wozu.hris.models.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*

    -----------------------------------------------------------------------------------
                                  TIMESHEET REPOSITORY
                                  created by Nathan Du
                                       10/20/2021
    -----------------------------------------------------------------------------------

 */

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {
    Timesheet findTopByEmployeeOrderByIdDesc(Employee employee);

    List<Timesheet> findTop3ByEmployee(Employee employee);

    List <Timesheet> findAllByEmployeeId(Long id);
}
