package com.wozu.hris.repositories;

import com.wozu.hris.models.Employee;
import com.wozu.hris.models.EmployeeTraining;
import com.wozu.hris.models.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeTrainingRepository extends JpaRepository<EmployeeTraining, Long> {
    List<EmployeeTraining> findAllByTrainingId(Long id);
    Optional<EmployeeTraining> findByEmployeeAndTraining(Employee employee, Training training);
}
