package com.wozu.hris.controllers;

import com.wozu.hris.models.Account;
import com.wozu.hris.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AccountController {
    @Autowired
    AccountService aService;

    // List all accounts rest api
    @GetMapping("/accounts")
    public List<Account> getAllAccounts(){
        return aService.allAccounts();
    }

    // Allows user input to register account.
    @PostMapping("/register")
    public String registerAccount(@Valid @RequestBody Account account, BindingResult result, HttpSession session){
        aService.registerCandidateAccount(account, result);
        if(result.hasErrors()) {
            return "register";
        }
        session.setAttribute("user_id", account.getId());
        return "redirect:/dashboard";
    }

    // Allows user input to login account.
    @PostMapping("login")
    public String loginAccount(@Valid @RequestBody Account account, BindingResult result, HttpSession session){
        Account acc = aService.authenticate(account, result);
        if(result.hasErrors()) {
            return "login";
        }
        session.setAttribute("user_id", account.getId());
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }
}
