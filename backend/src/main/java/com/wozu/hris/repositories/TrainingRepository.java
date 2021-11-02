package com.wozu.hris.repositories;

import com.wozu.hris.models.Training;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
    @Repository
    public interface TrainingRepository extends CrudRepository<Training, Long> {
        List<Training> findAll();
    }
