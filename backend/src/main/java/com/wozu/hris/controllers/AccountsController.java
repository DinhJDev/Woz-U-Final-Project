package com.wozu.hris.controllers;

import com.wozu.hris.models.Account;
import com.wozu.hris.repositories.AccountRepository;
import com.wozu.hris.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin (origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping ("/api/accounts")
public class AccountsController {
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository aRepo;

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

