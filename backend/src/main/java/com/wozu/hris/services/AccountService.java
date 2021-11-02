package com.wozu.hris.services;

import com.wozu.hris.models.Account;
import com.wozu.hris.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository aRepo;

    // Register account and hash their password
    public Account registerAccount(Account account, BindingResult result) {
        if(aRepo.findByUsername(account.getUsername()).isPresent()){
            result.rejectValue("username", "Unique", "This username is already in use!");
        }
        if(!account.getPassword().equals(account.getPasswordConfirmation())) {
            result.rejectValue("passwordConfirmation", "Matches", "The confirmation password and password must match!");
        }
        if(result.hasErrors()) {
            return null;
        } else {
            String hashed = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
            account.setPassword(hashed);
            return aRepo.save(account);
        }
    }

    // Find account by username
    public Optional<Account> findByUsername(String username) {
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
    public Account authenticate(Account account, BindingResult result) {
        if(result.hasErrors()) {
            return null;
        }
        Optional<Account> potentialAccount = aRepo.findByUsername(account.getUsername());
        if(!potentialAccount.isPresent()) {
            result.rejectValue("email", "Unique", "Unknown username!");
            return null;
        }
        Account acc = potentialAccount.get();
        if(!BCrypt.checkpw(account.getPassword(), acc.getPassword())) {
            result.rejectValue("password", "Matches", "Invalid Password!");
        }
        if(result.hasErrors()) {
            return null;
        } else {
            return acc;
        }
    }
}
