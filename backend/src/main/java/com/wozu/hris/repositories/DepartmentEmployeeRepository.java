package com.wozu.hris.repositories;

import com.wozu.hris.models.Department;
import com.wozu.hris.models.DepartmentEmployee;
import com.wozu.hris.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentEmployeeRepository extends JpaRepository<DepartmentEmployee, Long> {
    List<DepartmentEmployee> findByEmployeeAndDepartment(Employee e, Department d);
}
