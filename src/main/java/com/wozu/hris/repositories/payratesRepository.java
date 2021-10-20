package com.wozu.hris.repositories;

import com.wozu.hris.models.Payrates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface payratesRepository extends JpaRepository<Payrates, Integer> {
}
