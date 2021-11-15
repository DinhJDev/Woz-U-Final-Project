package com.wozu.hris.controllers;

import com.wozu.hris.models.Account;
import com.wozu.hris.models.ERole;
import com.wozu.hris.models.Role;
import com.wozu.hris.payload.response.MessageResponse;
import com.wozu.hris.repositories.AccountRepository;
import com.wozu.hris.repositories.RoleRepository;
import com.wozu.hris.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin (origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping ("/api/accounts")
public class AccountsController {
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository aRepo;
    @Autowired
    RoleRepository rRepo;

    // get all accounts
    @PreAuthorize("hasRole('HR') or hasRole('MANAGER')")
    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts(){
        try {
            List<Account> accounts = new ArrayList<Account>();

            aRepo.findAll().forEach(accounts::add);

            if(accounts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get account by id rest api
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MANAGER') or hasRole('HR')")
    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id){
        Account account = accountService.findAccountById(id);
        return ResponseEntity.ok(account);
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('HR')")
    @PutMapping("/promote/{id}")
    public ResponseEntity<?> promoteAccount(@PathVariable("id") Long id) {
        Account account = accountService.findAccountById(id);

        // Check Existing Roles
        if (account.getRoles().contains(ERole.ROLE_HR)) {
            return ResponseEntity.ok(new MessageResponse("This account is already in HR."));
        } else if (account.getRoles().contains(ERole.ROLE_MANAGER)) {
            Role newRole = rRepo.findByName(ERole.ROLE_HR).orElse(null);
            Set<Role> currentRoles = account.getRoles();
            currentRoles.add(newRole);
            account.setRoles(currentRoles);
            return ResponseEntity.ok(new MessageResponse("Account has been promoted to HR."));
        } else if (account.getRoles().contains(ERole.ROLE_EMPLOYEE)) {
            Role newRole = rRepo.findByName(ERole.ROLE_MANAGER).orElse(null);
            Set<Role> currentRoles = account.getRoles();
            currentRoles.add(newRole);
            account.setRoles(currentRoles);
            return ResponseEntity.ok(new MessageResponse("Account has been promoted to Manager."));
        } else if (account.getRoles().contains(ERole.ROLE_CANDIDATE)) {
            Role newRole = rRepo.findByName(ERole.ROLE_EMPLOYEE).orElse(null);
            Set<Role> currentRoles = account.getRoles();
            currentRoles.add(newRole);
            account.setRoles(currentRoles);
            return ResponseEntity.ok(new MessageResponse("Account has been promoted to Employee."));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Account isn't valid."));
        }
    }

    // delete account rest api
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteAccount(@PathVariable Long id){
        Account account = accountService.findAccountById(id);
        accountService.deleteAccount(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return  ResponseEntity.ok(response);
    }
}

