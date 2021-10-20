package com.wozu.hris.repositories;

import com.wozu.hris.models.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface timesheetRepository extends JpaRepository<Timesheet, Integer> {
}
