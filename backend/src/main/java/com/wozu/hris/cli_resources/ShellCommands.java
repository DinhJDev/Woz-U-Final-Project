package com.wozu.hris.cli_resources;

import com.wozu.hris.HrisApplication;
import com.wozu.hris.models.*;
import com.wozu.hris.repositories.AccountRepository;
import com.wozu.hris.repositories.RoleRepository;
import com.wozu.hris.services.AccountService;
import com.wozu.hris.services.DepartmentService;
import com.wozu.hris.services.EmployeeService;
import com.wozu.hris.services.PayrateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ShellCommands {

    private AccountRepository aRepo;
    private InputReader inputReader;
    private ShellResult shellResult;
    private AccountService aService;
    private EmployeeService eService;
    private RoleRepository rRepo;
    private PasswordEncoder bCryptPasswordEncoder;
    private PayrateService prService;
    private DepartmentService dService;

    public ShellCommands(AccountRepository aRepo, InputReader inputReader, ShellResult shellResult, AccountService aService, EmployeeService eService, RoleRepository rRepo, PasswordEncoder bCryptPasswordEncoder, PayrateService prService, DepartmentService dService){
        this.aRepo = aRepo;
        this.inputReader = inputReader;
        this.shellResult = shellResult;
        this.aService = aService;
        this.eService = eService;
        this.rRepo = rRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.prService = prService;
        this.dService = dService;
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
                shellResult.printInfo(line);
            }
        }catch(IOException e){
            shellResult.printError(e.toString());
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
        accountHolder = aService.registerCandidateAccount(newAccount, new Employee());

        return accountHolder;
    }

    public boolean promoteAccount(String type, String t){
        return promoteAccount(type, t, null);
    }

    public boolean promoteAccount(String type, String t, String role){
        boolean result = false;
        Account targetAccount = type.equalsIgnoreCase("Username") ? aService.findByUsername(t).get() : aService.findAccountById(Long.parseLong(t));

        if(role != null){
            if(role == "Employee"){
                result = aService.promoteCandidateAccount(targetAccount) != null ? true : false;
            }else if(role == "Manager"){
                result = aService.promoteEmployeeAccount(targetAccount) != null ? true : false;
            }
        }else{
            result = aService.promoteCandidateAccount(targetAccount) != null ? true : false;
        }

        return result;
    }

    public boolean promoteAccount(String type, String t, boolean hr){
        return promoteAccount(type, t, hr, null);
    }

    public boolean promoteAccount(String type, String t, boolean hr, String role){
        boolean result = false;
        Account targetAccount = type.equalsIgnoreCase("Username") ? aService.findByUsername(t).get() : aService.findAccountById(Long.parseLong(t));

        System.out.println(t);
        System.out.println(targetAccount);
        System.out.println(role);

        if(hr){
            if(role != null){
                if(role == "Employee"){
                    result = aService.promoteCandidateAccount(targetAccount) != null ? true : false;
                }else if(role == "Manager"){
                    result = aService.promoteEmployeeAccount(targetAccount) != null ? true : false;
                }else if(role == "HR"){
                    result = aService.promoteManagerAccount(targetAccount) != null ? true : false;
                }
            }else{
                result = aService.promoteCandidateAccount(targetAccount) != null ? true : false;
            }
        }

        System.out.println(result);

        return result;
    }

    public int getPermissionLevel(Set<Role> role){
        int permLvl = 0;
        for (Iterator<Role> it = role.iterator(); it.hasNext(); ) {
            Role r = it.next();
            if(r.getRoleId() > permLvl){
                permLvl = r.getRoleId();
            }
        }
        return permLvl;
    }

    public LinkedHashMap<String, Map<String, String>> getCommandGroup(int permLvl){
        LinkedHashMap<String, Map<String, String>> listedCommands = new LinkedHashMap<>();


        //Commands available to All Roles
        if(permLvl >= ERole.ROLE_CANDIDATE.getID()){
            Map<String, String> commands = new HashMap<>();

            //Role Restricted Commands
            if(permLvl == ERole.ROLE_CANDIDATE.getID()){
                commands.put("commandKey", "commandDetails");
            }

            listedCommands.put("Level 0", commands);
        }

        //Commands available to Employee+ Roles
        if(permLvl >= ERole.ROLE_EMPLOYEE.getID()){
            Map<String, String> commands = new HashMap<>();
            commands.put("cIn", "Clock In");
            commands.put("cOut", "Clock Out");
            commands.put("update", "Update Information");

            if(permLvl == ERole.ROLE_EMPLOYEE.getID()){

            }

            listedCommands.put("Level 1", commands);
        }

        //Commands available to Manager+ Roles
        if(permLvl >= ERole.ROLE_MANAGER.getID()){
            Map<String, String> commands = new HashMap<>();

            if(permLvl == ERole.ROLE_MANAGER.getID()){
                commands.put("performance", "Add performance review to Employee");
            }

            listedCommands.put("Level 2", commands);
        }

        //Commands available to HR Role
        if(permLvl >= ERole.ROLE_HR.getID()){
            Map<String, String> commands = new HashMap<>();
            commands.put("promote", "Promote Employee with Account");
            commands.put("edit", "Edit Employee");
            commands.put("deactivate", "Deactivate current Interface");

            if(permLvl == ERole.ROLE_HR.getID()){

            }

            listedCommands.put("Level 3", commands);
        }

        return listedCommands;
    }

    public void updateItem(String type, String item, Account targetUser) throws ParseException {
        updateItem(type, item, targetUser, false);
    }

    public void updateItem(String type, String item, Account targetUser, boolean hr) throws ParseException {
        boolean hSwitch = false;
        String newValue = "";
        if(item.equalsIgnoreCase("Date Of Birth")){
            newValue = inputReader.prompt("Input Date Of Birth (mm/dd/yyyy)");
        }else if(item.equalsIgnoreCase("Benefits")){
            newValue = "Benefits to be Done later";
        }else{
            if(hr && !item.equals("First Name") && !item.equals("Last Name")){
                hSwitch = true;
            }else{
                newValue = inputReader.prompt(String.format("Update %s", item));
            }
        }

        if(!hSwitch && !inputReader.confirmationPrompt(String.format("Confirm new %s: %s", item, newValue))){
            return;
        }

        Employee currentEmployee = targetUser.getEmployee();

        if(type.equalsIgnoreCase("Account Information")){
            if(item.equalsIgnoreCase("Username")){
                targetUser.setUsername(newValue);
            }else{
                targetUser.setPassword(bCryptPasswordEncoder.encode(newValue));
            }
            aRepo.save(targetUser);
        }else{
            if(item.equalsIgnoreCase("First Name")){
                currentEmployee.setFirstName(newValue);
            }else if(item.equalsIgnoreCase("Last Name")){
                currentEmployee.setLastName(newValue);
            }else if(item.equalsIgnoreCase("Date Of Birth")){
                try {
                    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
                    Date newDate = format.parse(newValue);
                    currentEmployee.setDateOfBirth(newDate);
                }catch(ParseException e){}
            }else if(item.equalsIgnoreCase("Payrate")){
                Payrate payrate = prService.existsByEmployee(currentEmployee) ? prService.findPayrateByEmployee(currentEmployee) : null;
                if(payrate != null){
                    Map<String, String> attributes = new HashMap<String, String>();
                    attributes.put("A", "Hourly Rate");
                    attributes.put("B", "Salary");
                    attributes.put("C", "Effective Date");
                    attributes.put("X", "FINISHED");
                    do {
                        clearConsole();
                        String selection = inputReader.listInput("Edit Payrate",
                                "Select an option [] above to update",
                                attributes,
                                true);

                        if(selection.equalsIgnoreCase("X")){
                            break;
                        }
                        do{
                            if(selection.equalsIgnoreCase("A")){
                                newValue = inputReader.prompt(attributes.get(selection));
                                if(inputReader.confirmationPrompt(newValue)){
                                    payrate.setHourlyRate(Double.parseDouble(newValue));
                                    break;
                                }
                            }else if(selection.equalsIgnoreCase("B")){
                                newValue = inputReader.prompt(attributes.get(selection));
                                if(inputReader.confirmationPrompt(newValue)){
                                    payrate.setSalary(Double.parseDouble(newValue));
                                    break;
                                }
                            }else if(selection.equalsIgnoreCase("C")){
                                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                                newValue = inputReader.prompt(String.format("%s (\"mm/dd/yyyy\")", attributes.get(selection)));
                                if(inputReader.confirmationPrompt(newValue)){
                                    payrate.setEffectiveDate(format.parse(newValue));
                                    break;
                                }
                            }

                        }while(true);
                    }while(true);

                    prService.updatePayrate(payrate.getId(), payrate);
                }else{
                    shellResult.printInfo("No Payrate found! Creating a new Payrate!");

                    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                    Double hourlyRate = 0.0;
                    Double salary = 0.0;
                    Date effectiveDate = new Date();
                    do{
                        shellResult.printInfo("Input \"exit\" to cancel!");
                        newValue = inputReader.prompt("Hourly Rate");
                        if(newValue.equalsIgnoreCase("exit")){
                            break;
                        }
                        hourlyRate = Double.parseDouble(newValue);
                        newValue = inputReader.prompt("Salary");
                        if(newValue.equalsIgnoreCase("exit")){
                            break;
                        }
                        salary = Double.parseDouble(newValue);
                        newValue = inputReader.prompt("Effective Date (\"mm/dd/yyyy\")");
                        if(newValue.equalsIgnoreCase("exit")){
                            break;
                        }

                        effectiveDate = format.parse(newValue);

                        if(inputReader.confirmationPrompt(String.format("Hourly Rate: $%.2f%nSalary: $%.2f%nEffective Date: %s", hourlyRate, salary, format.format(effectiveDate)))){
                            payrate = prService.createPayrate(new Payrate(currentEmployee));
                            payrate.setHourlyRate(hourlyRate);
                            payrate.setSalary(salary);
                            payrate.setEffectiveDate(effectiveDate);
                            prService.updatePayrate(payrate.getId(), payrate);
                            break;
                        }
                    }while(true);

                }
            }else if(item.equalsIgnoreCase("Department")){
                Map<String, String> departmentOptions = new HashMap<>();
                List<Department> list = dService.allDepts();
                ArrayList<DepartmentEmployee> currentDeps = new ArrayList(currentEmployee.getDepartment());
                for(int i = 0; i < list.size(); i++){
                    departmentOptions.put(String.valueOf((char)(i+65)), list.get(i).getName());
                }
                departmentOptions.put("X", "Cancel");
                do{
                    String selection = inputReader.listInput("Department",
                            "Select an option [] above",
                            departmentOptions,
                            true);
                    if(selection.equalsIgnoreCase("X")){
                        return;
                    }
                    if(inputReader.confirmationPrompt(String.format("Department: %s", departmentOptions.get(selection)))){


                    }
                }while(true);
            }else if(item.equalsIgnoreCase("Position")){

            }else if(item.equalsIgnoreCase("Training")){

            }else if(item.equalsIgnoreCase("Benefits")){

            }
            eService.updateEmployee(currentEmployee.getId(), currentEmployee);
        }

    }

}