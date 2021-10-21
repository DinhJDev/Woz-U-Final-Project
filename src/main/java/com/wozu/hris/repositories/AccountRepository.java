package com.wozu.hris.repositories;

import com.wozu.hris.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAll();

    Account findByUsername(String username);
}
