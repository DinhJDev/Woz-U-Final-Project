package com.wozu.hris.repositories;

import com.wozu.hris.models.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findAll();

    List<Training> findByTrainingNameNotIn(List<String> t);

    Optional<Training> findByTrainingName(String n);
}
