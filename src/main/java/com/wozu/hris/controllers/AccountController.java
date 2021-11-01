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

    @GetMapping("/")
    public String landingPage() {
        return "index";
    }

    @PostMapping("/register")
    public String registerAccount(@Valid @RequestBody Account account, BindingResult result, HttpSession session){
        aService.registerAccount(account, result);
        if(result.hasErrors()) {
            return "index";
        }
        session.setAttribute("user_id", account.getId());
        return "redirect:/dashboard";
    }

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
