package com.wozu.hris.cli_resources;

import com.wozu.hris.HrisApplication;
import com.wozu.hris.models.*;
import com.wozu.hris.repositories.AccountRepository;
import com.wozu.hris.repositories.RoleRepository;
import com.wozu.hris.services.*;
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
    private DepartmentEmployeeService dEService;
    private PositionService pService;
    private BenefitService bService;
    private TrainingService tService;
    private EmployeeTrainingService eTService;

    public ShellCommands(AccountRepository aRepo, InputReader inputReader, ShellResult shellResult, AccountService aService, EmployeeService eService, RoleRepository rRepo, PasswordEncoder bCryptPasswordEncoder, PayrateService prService, DepartmentService dService, DepartmentEmployeeService dEService, PositionService pService, BenefitService bService, TrainingService tService, EmployeeTrainingService eTService){
        this.aRepo = aRepo;
        this.inputReader = inputReader;
        this.shellResult = shellResult;
        this.aService = aService;
        this.eService = eService;
        this.rRepo = rRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.prService = prService;
        this.dService = dService;
        this.dEService = dEService;
        this.pService = pService;
        this.bService = bService;
        this.tService = tService;
        this.eTService = eTService;
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

    public String getPermissionString(Set<Role> role){
        int permLvl = 0;
        for (Iterator<Role> it = role.iterator(); it.hasNext(); ) {
            Role r = it.next();
            if(r.getRoleId() > permLvl){
                permLvl = r.getRoleId();
            }
        }
        if(permLvl <= ERole.ROLE_MANAGER.getID()){
            if(permLvl == ERole.ROLE_EMPLOYEE.getID()){
                return "Employee";
            }else if(permLvl == ERole.ROLE_CANDIDATE.getID()){
                return "Candidate";
            }else{
                return "Manager";
            }
        }else{
            return "HR";
        }
    }

    public LinkedHashMap<String, Map<String, String>> getCommandGroup(int permLvl){
        LinkedHashMap<String, Map<String, String>> listedCommands = new LinkedHashMap<>();


        //Commands available to All Roles
        if(permLvl >= ERole.ROLE_CANDIDATE.getID()){
            Map<String, String> commands = new HashMap<>();

            commands.put("signOut", "Sign out of session");
            //Role Restricted Commands
            if(permLvl == ERole.ROLE_CANDIDATE.getID()){
                commands.put("view", "View Company Information");
            }

            listedCommands.put("Level 0", commands);
        }

        //Commands available to Employee+ Roles
        if(permLvl >= ERole.ROLE_EMPLOYEE.getID()){
            Map<String, String> commands = new HashMap<>();
            commands.put("cIn", "Clock In");
            commands.put("cOut", "Clock Out");
            commands.put("update", "Update Information");
            commands.put("info", "View Employee Information");

            if(permLvl == ERole.ROLE_EMPLOYEE.getID()){

            }

            listedCommands.put("Level 1", commands);
        }

        //Commands available to Manager+ Roles
        if(permLvl >= ERole.ROLE_MANAGER.getID()){
            Map<String, String> commands = new HashMap<>();

            commands.put("view-performance", "View performance review of Employee");

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
            commands.put("manage", "Manage Company");

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
                clearConsole();
                Map<String, String> departmentOptions = new HashMap<>();
                List<Department> list = dService.allDepts();
                for(int i = 0; i < list.size(); i++){
                    departmentOptions.put(String.valueOf((char)(i+65)), list.get(i).getName());
                }
                departmentOptions.put("X", "FINISHED/CANCEL");
                do{
                    String selection = inputReader.listInput("Department",
                            "Select an option [] above",
                            departmentOptions,
                            true);
                    if(selection.equalsIgnoreCase("X")){

                        return;
                    }

                    if(inputReader.confirmationPrompt(String.format("Department: %s", departmentOptions.get(selection)))){
                        if(dEService.existsByEmployeeAndDepartment(currentEmployee, dService.findByDeptName(departmentOptions.get(selection)))){
                            DepartmentEmployee holder = dEService.findByEmployeeAndDepartment(currentEmployee, dService.findByDeptName(departmentOptions.get(selection)));
                            Map<String, String> options = new HashMap<>();
                            options.put("A", "Edit Position");
                            options.put("B", "Remove from Department");
                            options.put("X", "FINISHED/CANCEL");
                            clearConsole();
                            shellResult.printInfo(String.format("Department: %s", departmentOptions.get(selection)));
                            String optionSelection = inputReader.listInput("Position",
                                    "Select an option [] above",
                                    options,
                                    true);
                            if(optionSelection.equalsIgnoreCase("X")){
                                break;
                            }else if(optionSelection.equalsIgnoreCase("A")){
                                clearConsole();
                                shellResult.printInfo(String.format("Current Position: %s", holder.getPosition().getName()));
                                List<Position> posList = pService.allPos();
                                Map<String, String> posOptions = new HashMap<>();
                                for(int i = 0; i < posList.size(); i++){
                                    posOptions.put(String.valueOf((char)(i+65)), posList.get(i).getName());
                                }
                                do {
                                    String posSelection = inputReader.listInput("Position",
                                            "Select an option [] above",
                                            posOptions,
                                            true);
                                    if (inputReader.confirmationPrompt(String.format("Position: %s", posOptions.get(posSelection)))) {
                                        clearConsole();
                                        shellResult.printSuccess("Updated Position Successfully!");
                                        holder.setPosition(pService.findByName(posOptions.get(posSelection)));
                                        dEService.updateDepartmentEmployee(holder.getId(), holder);
                                        break;
                                    }
                                }while(true);
                            }else if(optionSelection.equalsIgnoreCase("B")){
                                if(inputReader.confirmationPrompt(String.format("Remove from %s Department?", departmentOptions.get(selection)))){
                                    clearConsole();
                                    shellResult.printSuccess("Removed from Department Successfully");
                                    dEService.deleteDepartmentEmployee(holder.getId());
                                    break;
                                }
                            }
                        }else{
                            clearConsole();
                            shellResult.printInfo(String.format("Employee is currently not in %s Department!", departmentOptions.get(selection)));
                            if(inputReader.confirmationPrompt("Add Employee into Department?")){
                                  List<Position> posList = pService.allPos();
                                  Map<String, String> posOptions = new HashMap<>();
                                  for(int i = 0; i < posList.size(); i++){
                                      posOptions.put(String.valueOf((char)(i+65)), posList.get(i).getName());
                                  }
                                  do {
                                      String posSelection = inputReader.listInput("Position",
                                              "Select an option [] above",
                                              posOptions,
                                              true);
                                      if(inputReader.confirmationPrompt(String.format("Position: %s", posOptions.get(posSelection)))){
                                          clearConsole();
                                          shellResult.printSuccess("Employee Added into Department Successfully!");
                                          Department d = dService.findByDeptName(departmentOptions.get(selection));
                                          dEService.createDepartmentEmployee(new DepartmentEmployee(d, currentEmployee, pService.findByName(posOptions.get(posSelection))));
                                          break;
                                      }else{
                                          clearConsole();
                                      }
                                  }while(true);
                            }else{
                                clearConsole();
                            }
                        }

                    }else{
                        clearConsole();
                    }
                }while(true);
            }else if(item.equalsIgnoreCase("Training")){
                clearConsole();
                if(eTService.existsByEmployee(currentEmployee)){
                    Map<String, String> option = new HashMap<>();
                    option.put("A", "Add");
                    option.put("B", "Remove");
                    option.put("X", "FINISH/CANCEL");

                    Map<String, String> trainingOptions = new HashMap<>();

                    String selection;

                    do{
                        selection = inputReader.listInput("Training",
                                "Select an option [] provided above.",
                                option,
                                true);
                        if(selection.equalsIgnoreCase("X")){
                            clearConsole();
                            break;
                        }

                        if(selection.equalsIgnoreCase("A")){
                            clearConsole();
                            List<EmployeeTraining> listedTrainings = eTService.findAllByEmployee(currentEmployee);
                            List<String> ArrayListedTrainings = new ArrayList<>(listedTrainings.size());
                            List<Training> nonListedTrainings;

                            for(EmployeeTraining e : listedTrainings){
                                ArrayListedTrainings.add(e.getTraining().getTrainingName());
                            }

                            nonListedTrainings = tService.findAllNotIn(ArrayListedTrainings);
                            String selectedTraining;

                            if(nonListedTrainings.size() > 0){
                                for(int i = 0; i < nonListedTrainings.size(); i++){
                                    trainingOptions.put(String.valueOf((char)(i+65)), nonListedTrainings.get(i).getTrainingName());
                                }

                                trainingOptions.put("X", "FINISH/CANCEL");

                                do{
                                    selectedTraining = inputReader.listInput("Training",
                                            "Select an option [] provided above",
                                            trainingOptions,
                                            true);

                                    if(selectedTraining.equalsIgnoreCase("X")){
                                        clearConsole();
                                        break;
                                    }

                                    if(inputReader.confirmationPrompt(String.format("Add Training: %s", trainingOptions.get(selectedTraining)))){
                                        clearConsole();
                                        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                                        String completionDate = inputReader.prompt("Date of Completion(mm/dd/yyyy)");
                                        clearConsole();
                                        if(inputReader.confirmationPrompt(String.format("Training: %s%nDate Of Completion: %s", trainingOptions.get(selectedTraining), completionDate))){
                                            clearConsole();
                                            eTService.createEmployeeTraining(new EmployeeTraining(currentEmployee, tService.findByTrainingName(trainingOptions.get(selectedTraining)), format.parse(completionDate)));
                                            shellResult.printSuccess("Added Training Successfully!");
                                            return;
                                        }
                                    }

                                }while(true);
                            }else{
                                clearConsole();
                                shellResult.printInfo("No Available Trainings!");
                                break;
                            }


                        }else if(selection.equalsIgnoreCase("B")){
                            clearConsole();
                            String selectedTraining;

                            do{
                                List<EmployeeTraining> listedTrainings = eTService.findAllByEmployee(currentEmployee);
                                trainingOptions = new HashMap<>();
                                for(int i = 0; i < listedTrainings.size(); i++){
                                    trainingOptions.put(String.valueOf((char)(i+65)), listedTrainings.get(i).getTraining().getTrainingName());
                                }
                                trainingOptions.put("X", "FINISH/CANCEL");

                                selectedTraining = inputReader.listInput("Training",
                                        "Select an option [] provided above.",
                                        trainingOptions,
                                        true);

                                if(selectedTraining.equalsIgnoreCase("X")){
                                    clearConsole();
                                    break;
                                }
                                if(inputReader.confirmationPrompt(String.format("Remove Training: %s", trainingOptions.get(selectedTraining)))){
                                    clearConsole();
                                    eTService.deleteEmployeeTraining(eTService.findByEmployeeAndTraining(currentEmployee, tService.findByTrainingName(trainingOptions.get(selectedTraining))).getId());
                                    shellResult.printSuccess("Removed Training Successfully!");
                                    return;
                                }else{
                                    clearConsole();
                                }
                            }while(true);

                        }else{
                            clearConsole();
                            break;
                        }
                    }while(true);
                }else{
                    Map<String, String> trainingOptions = new HashMap<>();
                    shellResult.printInfo("No Recorded Trainings!");
                    if(inputReader.confirmationPrompt("Add Training to Employee?")){
                        String selectedTraining;

                        List<Training> listedTrainings = tService.allTrainings();
                        for(int i = 0; i < listedTrainings.size(); i++){
                            trainingOptions.put(String.valueOf((char)(i+65)), listedTrainings.get(i).getTrainingName());
                        }
                        trainingOptions.put("X", "FINISH/CANCEL");

                        do{
                            selectedTraining = inputReader.listInput("Training",
                                    "Select an option [] provided above.",
                                    trainingOptions,
                                    true);
                           if(selectedTraining.equalsIgnoreCase("X")){
                               clearConsole();
                               break;
                           }

                           if(inputReader.confirmationPrompt(String.format("Add Training: %s", trainingOptions.get(selectedTraining)))){
                               clearConsole();
                               SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                               String completionDate = inputReader.prompt("Date of Completion(mm/dd/yyyy)");
                               clearConsole();
                               if(inputReader.confirmationPrompt(String.format("Training: %s%nDate Of Completion: %s", trainingOptions.get(selectedTraining), completionDate))){
                                   clearConsole();
                                   eTService.createEmployeeTraining(new EmployeeTraining(currentEmployee, tService.findByTrainingName(trainingOptions.get(selectedTraining)), format.parse(completionDate)));
                                   shellResult.printSuccess("Added Training Successfully!");
                                   return;
                               }
                           }

                        }while(true);

                    }else{
                        clearConsole();
                        return;
                    }
                }

            }else if(item.equalsIgnoreCase("Benefits")){
                clearConsole();
                String selection;
                Map<String, String> benefitOptions = new HashMap<>();
                List<Benefit> listedBenefits = bService.allBenefits();

                for(int i = 0; i < listedBenefits.size(); i++){
                    benefitOptions.put(String.valueOf((char)(i+65)), listedBenefits.get(i).getName());
                }
                benefitOptions.put("X", "CANCEL");


                if(bService.existsByEmployee(currentEmployee)){
                    shellResult.printInfo(String.format("Current Benefit Package: %s", currentEmployee.getBenefit().getName()));
                    if(inputReader.confirmationPrompt("Would you like to change the current Package?")) {
                        do{

                            selection = inputReader.listInput("Benefit Package",
                                    "Select a Benefit Package option [] above.",
                                    benefitOptions,
                                    true);

                            if(selection.equalsIgnoreCase("X")){
                                clearConsole();
                                return;
                            }

                            if (inputReader.confirmationPrompt(String.format("Benefit Package: %s", benefitOptions.get(selection)))) {
                                clearConsole();
                                currentEmployee.setBenefit(bService.findByName(benefitOptions.get(selection)));
                                shellResult.printSuccess("Successfully Changed Benefit Package!");
                                break;
                            }else{
                                clearConsole();
                            }

                        }while(true);

                    }else{
                        clearConsole();
                        return;
                    }
                }else{
                   shellResult.printInfo("Employee has no Benefit Package!");
                   if(inputReader.confirmationPrompt("Would you like to add a Benefit Package?")){
                       do{

                           selection = inputReader.listInput("Benefit Package",
                                   "Select a Benefit Package option [] above.",
                                   benefitOptions,
                                   true);

                           if(selection.equalsIgnoreCase("X")){
                               clearConsole();
                               return;
                           }

                           if (inputReader.confirmationPrompt(String.format("Benefit Package: %s", benefitOptions.get(selection)))) {
                               clearConsole();
                               currentEmployee.setBenefit(bService.findByName(benefitOptions.get(selection)));
                               shellResult.printSuccess("Successfully Changed Benefit Package!");
                               break;
                           }else{
                               clearConsole();
                           }

                       }while(true);
                   }else{
                       clearConsole();
                       return;
                   }
                }
            }
            eService.updateEmployee(currentEmployee.getId(), currentEmployee);
        }

    }

}