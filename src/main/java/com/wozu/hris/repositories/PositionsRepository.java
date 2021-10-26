package com.wozu.hris.repositories;

import com.wozu.hris.models.Positions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Positions repository
//created: 10/25/21
//updated: 10/25/21 AF

@Repository
public interface PositionsRepository extends JpaRepository<Positions, Long> {
    Positions findByPosName(String pos_name);
}
