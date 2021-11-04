package com.wozu.hris.security.services;

import com.wozu.hris.models.Account;
import com.wozu.hris.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    AccountRepository aRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = aRepo.findByUsernameIgnoreCase(username)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(account);
    }
}
