package com.wozu.hris.repositories;

import com.wozu.hris.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Position repository
//created: 10/25/21
//updated: 10/25/21 AF

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    Position findByPosName(String pos_name);
}
