package com.wozu.hris.repositories;

import com.wozu.hris.models.Employee;
import com.wozu.hris.models.EmployeeTraining;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeTrainingRepository extends JpaRepository<EmployeeTraining, Long> {
    List<EmployeeTraining> findAllByTrainingId(Long id);
}
