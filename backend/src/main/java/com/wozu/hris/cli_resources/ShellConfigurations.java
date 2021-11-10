package com.wozu.hris.cli_resources;

import com.wozu.hris.repositories.AccountRepository;
import com.wozu.hris.repositories.RoleRepository;
import com.wozu.hris.services.AccountService;
import com.wozu.hris.services.EmployeeService;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ShellConfigurations {

    @Bean
    public ShellResult shellResult(@Lazy Terminal terminal){
        return new ShellResult(terminal);
    }

    @Bean
    public InputReader inputReader(@Lazy LineReader lineReader, ShellResult shellResult){return new InputReader(lineReader, shellResult);}

    @Bean
    public ShellCommands shellCommands(@Lazy AccountRepository aRepo, InputReader inputReader, ShellResult shellResult, AccountService aService, EmployeeService eService, RoleRepository rRepo, PasswordEncoder bCryptPasswordEncoder){
        return new ShellCommands(aRepo, inputReader, shellResult, aService, eService, rRepo, bCryptPasswordEncoder);
    }
}
