package com.wozu.hris.cli_resources;

import com.wozu.hris.HrisApplication;
import com.wozu.hris.models.Account;
import com.wozu.hris.models.Employee;
import com.wozu.hris.repositories.AccountRepository;
import com.wozu.hris.services.AccountService;
import com.wozu.hris.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Date;

public class ShellCommands {


    private AccountRepository aRepo;

    private InputReader inputReader;

    private ShellResult shellResult;

    private AccountService aService;

    private EmployeeService eService;

    public ShellCommands(AccountRepository aRepo, InputReader inputReader, ShellResult shellResult, AccountService aService, EmployeeService eService){
        this.aRepo = aRepo;
        this.inputReader = inputReader;
        this.shellResult = shellResult;
        this.aService = aService;
        this.eService = eService;
    }

    public void clearConsole(){
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }

    public void displayBanner(){
        try{
            //Paths.get("backend", "src", "main", "resources", "banner.txt"))
            String filepath = new File("").getAbsolutePath();
            BufferedReader read = new BufferedReader(new FileReader(filepath.concat("/src/main/resources/banner.txt")));
            String line;
            while((line = read.readLine()) != null){
                System.out.println(line);
            }
        }catch(IOException e){
            System.out.println(e);
        }
    }

    public Account createAccount(){
        Account accountHolder;
        String username = null;
        String password = null;

        do{
            shellResult.printInfo("Create Username (input \"quit\" to exit)");
            String holder_username = inputReader.prompt("Username");
            if(!StringUtils.hasText(holder_username)){
                shellResult.printError("Invalid Input! Username can not be blank or contain blanks!");
                continue;
            }else if(holder_username.length() < 5 && !holder_username.equalsIgnoreCase("quit")){
                shellResult.printError("Username must be more than 5 Character!");
                continue;
            }else if(holder_username.equalsIgnoreCase("quit")){
                return null;
            }
            if (aRepo.existsByUsernameIgnoreCase(holder_username)) {
                shellResult.printError("Username is already in use!");
            }else{
                username = holder_username;
            }
        }while(username == null);

        do{
            shellResult.printInfo("Create Password (input \"quit\" to exit)");
            String holder_password = inputReader.prompt("Password", null, false);
            String c_password = inputReader.prompt("Confirm Password", null, false);
            if(!StringUtils.hasText(holder_password)){
                shellResult.printError("Password can not be blank!");
                continue;
            }else if(holder_password.length() < 5 && !holder_password.equalsIgnoreCase("quit")){
                shellResult.printError("Password must be atleast 5 Characters!");
                continue;
            }else if(holder_password.equalsIgnoreCase("quit")){
                return null;
            }
            if (!holder_password.equals(c_password)) {
                shellResult.printError("Confirmation Password is not the same as Password!");
            }else{
                password = holder_password;
            }
        }while(password == null);


        Account newAccount = new Account(username, password);
        accountHolder = aService.registerCandidateAccount(newAccount);

        return accountHolder;
    }

}