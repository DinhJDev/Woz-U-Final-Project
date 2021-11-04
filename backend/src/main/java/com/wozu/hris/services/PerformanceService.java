package com.wozu.hris.services;
import com.wozu.hris.models.Performance;
import com.wozu.hris.repositories.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class PerformanceService {
    @Autowired
    PerformanceRepository performanceRepository;

    public List<Performance> allPerformances() {
        return performanceRepository.findAll();
    }

    public List<Performance> allEmployeePerformance(Long id) {
        return performanceRepository.findAllByRevieweeId(id);
    }

    public Performance createPerformance(Performance e) {
        return performanceRepository.save(e);
    }
    public Performance findPerformance(Long id) {
        Optional<Performance> optionalPerformance = performanceRepository.findById(id);
        if(optionalPerformance.isPresent()) {
            return optionalPerformance.get();
        } else {
            return null;
        }
    }
    public Performance updatePerformance(Long id, Performance performance) {
        Optional<Performance> optionalE = performanceRepository.findById(id);
        if(optionalE.isPresent()) {
            return performanceRepository.save(performance);
        } else {
            return null;
        }
    }
    public void deletePerformance(Long id) {
        this.performanceRepository.deleteById(id);
    }
}