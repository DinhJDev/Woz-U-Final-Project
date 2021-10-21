package com.wozu.hris.services;

import com.wozu.hris.models.Account;
import com.wozu.hris.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository aRepo;

    // Register account and hash their password
    public Account registerAccount(Account account) {
        String hashed = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
        account.setPassword(hashed);
        return aRepo.save(account);
    }

    // Find account by username
    public Account findByUsername(String username) {
        return aRepo.findByUsername(username);
    }

    // Find account by id
    public Account findAccountById(Long id) {
        Optional<Account> a = aRepo.findById(id);
        if(a.isPresent()) {
            return a.get();
        } else {
            return null;
        }
    }

    // Authenticate account
    public boolean authenticateAccount(String username, String password) {
        Account account = aRepo.findByUsername(username);
        if (account == null) {
            return false;
        } else {
            if(BCrypt.checkpw(password, account.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }
}
