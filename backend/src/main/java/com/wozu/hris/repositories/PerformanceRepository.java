package com.wozu.hris.repositories;

import com.wozu.hris.models.Performance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
    @Repository
public interface PerformanceRepository extends CrudRepository<Performance, Long> {
    List<Performance> findAll();

    // Employee being reviewed (Employee)
    List<Performance> findAllByRevieweeId(Long id);

    // Employee reviewing (Manager)
    List<Performance> findAllByReviewerId(Long Id);
}