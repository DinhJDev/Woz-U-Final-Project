package com.wozu.hris.controllers;

import com.wozu.hris.models.Account;
import com.wozu.hris.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@RestController
@RequestMapping("")
public class AccountController {
    @Autowired
    AccountService aService;

    // Returns index.html (Landing Page) for user to view.
    @GetMapping("/")
    public String landingPage() {
        return "index";
    }

    // Allows user input to register account.
    @PostMapping("/register")
    public String registerAccount(@Valid @RequestBody Account account, BindingResult result, HttpSession session){
        aService.registerAccount(account, result);
        if(result.hasErrors()) {
            return "index";
        }
        session.setAttribute("user_id", account.getId());
        return "redirect:/dashboard";
    }

    // Allows user input to login account.
    @PostMapping("login")
    public String loginAccount(@Valid @RequestBody Account account, BindingResult result, HttpSession session){
        Account acc = aService.authenticate(account, result);
        if(result.hasErrors()) {
            return "index";
        }
        session.setAttribute("user_id", account.getId());
        return "redirect:/dashboard";
    }

}
