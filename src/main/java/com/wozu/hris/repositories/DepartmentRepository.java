package com.wozu.hris.repositories;

import com.wozu.hris.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Department repository
//created: 10/25/21
//updated: 10/25/21 AF

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByDeptName(String dept_name);
}
