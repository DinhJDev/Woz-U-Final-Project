package com.wozu.hris.repositories;

import com.wozu.hris.models.Payrolls;
import org.springframework.data.jpa.repository.JpaRepository;

public interface payrollsRepository extends JpaRepository<Payrolls, Integer> {
}
