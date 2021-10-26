package com.wozu.hris.repositories;

import com.wozu.hris.models.Departments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Departments repository
//created: 10/25/21
//updated: 10/25/21 AF

@Repository
public interface DepartmentsRepository extends JpaRepository<Departments, Long> {
    Departments findByDeptName(String dept_name);
}
