package com.wozu.hris.repositories;

import com.wozu.hris.models.Employee;
import com.wozu.hris.models.EmployeeTraining;
import com.wozu.hris.models.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeTrainingRepository extends JpaRepository<EmployeeTraining, Long> {

    Optional<EmployeeTraining> findByEmployee(Employee e);

    Boolean existsByEmployee(Employee e);

    Boolean existsByEmployeeAndTraining(Employee e, Training t);

    List<EmployeeTraining> findAllByEmployee(Employee e);

    List<EmployeeTraining> findAllByTraining(Training t);

    Optional<EmployeeTraining> findByEmployeeAndTraining(Employee e, Training t);

    List<EmployeeTraining> findAllByTrainingId(Long id);

}
