package com.wozu.hris.repositories;

import com.wozu.hris.models.Department;
import com.wozu.hris.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAll();

    List<Employee> findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(String firstName, String lastName);

    Optional<Employee> findByAccountUsernameIgnoreCase(String username);

    List<Employee> findByDepartmentEmployeeDepartment(Department department);
}