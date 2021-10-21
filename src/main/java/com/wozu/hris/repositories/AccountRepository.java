package com.wozu.hris.repositories;

import com.wozu.hris.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAll();
    
    Account findByUsername(String username);
}
