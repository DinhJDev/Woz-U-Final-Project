package com.wozu.hris.services;

import com.wozu.hris.models.Account;
import com.wozu.hris.models.ERole;
import com.wozu.hris.models.Employee;
import com.wozu.hris.models.Role;
import com.wozu.hris.repositories.AccountRepository;
import com.wozu.hris.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountService {
    @Autowired
    AccountRepository aRepo;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    RoleRepository rRepo;

    // Returns all the accounts
    public List<Account> allAccounts() {
        return aRepo.findAll();
    }

    // Register candidate account and hash their password
    public Account registerCandidateAccount(Account account, Employee employee) {
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        Set<Role> roles = account.getRoles();
        roles.add(rRepo.findByName(ERole.ROLE_CANDIDATE).orElseThrow(()-> new RuntimeException("Error: Role is not found.")));
        account.setRoles(roles);
        account.setEmployee(employee);
        return aRepo.save(account);
    }

    // Register candidate account without personal data (name & dob)
    public Account registerEmployeeAccount(Account account) {
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        Set<Role> roles = account.getRoles();
        roles.add(rRepo.findByName(ERole.ROLE_EMPLOYEE).orElseThrow(()-> new RuntimeException("Error: Role is not found.")));
        account.setRoles(roles);
        Employee e = new Employee();
        account.setEmployee(e);
        return aRepo.save(account);
    }
    // Promotes Candidate Account to Employee Account
    public Account promoteCandidateAccount(Account account) {
        Set<Role> roles = account.getRoles();
        if(aRepo.findByUsernameIgnoreCase(account.getUsername()).isPresent()){
            if (roles.contains(rRepo.findByName(ERole.ROLE_EMPLOYEE))){
                // Account already has Employee Role.
                return null;
            } else {
                roles.add(rRepo.findByName(ERole.ROLE_EMPLOYEE).orElseThrow(()-> new RuntimeException("Error: Role is not found.")));
                account.setRoles(roles);
                return aRepo.save(account);
            }
        } else {
            // Account doesn't exist.
            return null;
        }
    }
    // Promotes Employee Account to Manager Account
    public Account promoteEmployeeAccount(Account account) {
        Set<Role> roles = account.getRoles();
        if(aRepo.findByUsernameIgnoreCase(account.getUsername()).isPresent()){
            if (roles.contains(rRepo.findByName(ERole.ROLE_MANAGER))){
                // Account already has Employee Role.
                return null;
            } else {
                roles.add(rRepo.findByName(ERole.ROLE_MANAGER).orElseThrow(()-> new RuntimeException("Error: Role is not found.")));
                account.setRoles(roles);
                return aRepo.save(account);
            }
        } else {
            // Account doesn't exist.
            return null;
        }
    }

    // Promotes Manager Account to HR Account
    public Account promoteManagerAccount(Account account){
        System.out.println(account);
        Set<Role> roles = account.getRoles();
        System.out.println(aRepo.findByUsernameIgnoreCase(account.getUsername()).isPresent());
        if(aRepo.findByUsernameIgnoreCase(account.getUsername()).isPresent()){
            if(roles.contains(rRepo.findByName(ERole.ROLE_HR))){
                return null;
            }else{
                System.out.println("Check");
                roles.add(rRepo.findByName(ERole.ROLE_HR).orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
                account.setRoles(roles);
                return aRepo.save(account);
            }
        }else{
            return null;
        }
    }

    // Find account by username
    public Optional<Account> findByUsername(String username) {
        return aRepo.findByUsernameIgnoreCase(username);
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
    public Account authenticate(Account account) {
        Optional<Account> potentialAccount = aRepo.findByUsernameIgnoreCase(account.getUsername());
        if(!potentialAccount.isPresent()) {
            return null;
        }
        Account acc = potentialAccount.get();
        if(!bCryptPasswordEncoder.matches(account.getPassword(), acc.getPassword())) {
            return null;
        }
        return acc;
    }
    // Delete an employee
    public void deleteAccount(Long id) {
        this.aRepo.deleteById(id);
    }

    public Boolean existsById(Long id){
        return aRepo.existsById(id);
    }
}