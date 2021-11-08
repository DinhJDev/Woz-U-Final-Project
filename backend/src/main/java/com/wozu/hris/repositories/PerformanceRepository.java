package com.wozu.hris.repositories;

import com.wozu.hris.models.Performance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
    @Repository
public interface PerformanceRepository extends CrudRepository<Performance, Long> {
    List<Performance> findAll();

    List<Performance> findAllByRevieweeId(Long id);
}