package com.wozu.hris.services;

import com.wozu.hris.models.Account;
import com.wozu.hris.models.ERole;
import com.wozu.hris.models.Role;
import com.wozu.hris.repositories.AccountRepository;
import com.wozu.hris.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountService {
    @Autowired
    AccountRepository aRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    RoleRepository rRepo;

    // Returns all the accounts
    public List<Account> allAccounts() {
        return aRepo.findAll();
    }

    // Register candidate account and hash their password
    public Account registerCandidateAccount(Account account, BindingResult result) {
        if(aRepo.findByUsername(account.getUsername()).isPresent()){
            result.rejectValue("username", "Unique", "This username is already in use!");
        }
        if(!account.getPassword().equals(account.getPasswordConfirmation())) {
            result.rejectValue("passwordConfirmation", "Matches", "The confirmation password and password must match!");
        }
        if(result.hasErrors()) {
            return null;
        } else {
            account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
            Set<Role> roles = account.getRoles();
            roles.add(rRepo.findByName(ERole.ROLE_CANDIDATE));
            account.setRoles(roles);
            return aRepo.save(account);
        }
    }

    // Register candidate account and hash their password
    public Account registerEmployeeAccount(Account account, BindingResult result) {
        if(aRepo.findByUsername(account.getUsername()).isPresent()){
            result.rejectValue("username", "Unique", "This username is already in use!");
        }
        if(!account.getPassword().equals(account.getPasswordConfirmation())) {
            result.rejectValue("passwordConfirmation", "Matches", "The confirmation password and password must match!");
        }
        if(result.hasErrors()) {
            return null;
        } else {
            account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
            Set<Role> roles = account.getRoles();
            roles.add(rRepo.findByName(ERole.ROLE_EMPLOYEE));
            account.setRoles(roles);
            return aRepo.save(account);
        }
    }
    // Promotes Candidate Account to Employee Account
    public Account promoteCandidateAccount(Account account) {
        Set<Role> roles = account.getRoles();
        if(aRepo.findByUsername(account.getUsername()).isPresent()){
            if (roles.contains(rRepo.findByName(ERole.ROLE_EMPLOYEE))){
                // Account already has Employee Role.
                return null;
            } else {
                roles.add(rRepo.findByName(ERole.ROLE_EMPLOYEE));
                account.setRoles(roles);
                return aRepo.save(account);
            }
        } else {
            // Account doesn't exist.
            return null;
        }
    }
    // Promotes Employee Account to HR Account
    public Account promoteEmployeeAccount(Account account) {
        Set<Role> roles = account.getRoles();
        if(aRepo.findByUsername(account.getUsername()).isPresent()){
            if (roles.contains(rRepo.findByName(ERole.ROLE_CANDIDATE))){
                // Account already has Employee Role.
                return null;
            } else {
                roles.add(rRepo.findByName(ERole.ROLE_CANDIDATE));
                account.setRoles(roles);
                return aRepo.save(account);
            }
        } else {
            // Account doesn't exist.
            return null;
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

    // Authenticate/Login account
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
        if(!bCryptPasswordEncoder.matches(account.getPassword(), acc.getPassword())) {
            result.rejectValue("password", "Matches", "Invalid Password!");
        }
        if(result.hasErrors()) {
            return null;
        } else {
            return acc;
        }
    }
}
