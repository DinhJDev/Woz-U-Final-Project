package com.wozu.hris.controllers;

import com.wozu.hris.models.Account;
import com.wozu.hris.payload.request.LoginRequest;
import com.wozu.hris.payload.request.SignupRequest;
import com.wozu.hris.payload.response.JwtResponse;
import com.wozu.hris.payload.response.MessageResponse;
import com.wozu.hris.repositories.AccountRepository;
import com.wozu.hris.security.jwt.JwtUtils;
import com.wozu.hris.security.services.UserDetailsImpl;
import com.wozu.hris.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    AccountService aService;
    @Autowired
    AccountRepository aRepo;
    @Autowired
    JwtUtils jwtUtils;


    // Allows user input to register account.
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (aRepo.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User is already taken!"));
        }
        if(!signupRequest.getPassword().equals(signupRequest.getPasswordConfirmation())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: The confirmation password and password must match!"));
        }
        Account account = new Account(signupRequest.getUsername(), signupRequest.getPassword());
        aService.registerCandidateAccount(account);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    // Allows user input to login account.
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }


}
