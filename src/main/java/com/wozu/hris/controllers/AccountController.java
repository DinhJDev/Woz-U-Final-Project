package com.wozu.hris.controllers;

import com.wozu.hris.models.Account;
import com.wozu.hris.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
@RequestMapping("")
public class AccountController {
    @Autowired
    AccountService aService;

    // Returns homepage.
    @GetMapping("/")
    public String homePage() {
        return "homepage";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    // Allows user input to register account.
    @PostMapping("/register")
    public String registerAccount(@Valid @RequestBody Account account, BindingResult result, HttpSession session){
        aService.registerAccount(account, result);
        if(result.hasErrors()) {
            return "register";
        }
        session.setAttribute("user_id", account.getId());
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
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
        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
