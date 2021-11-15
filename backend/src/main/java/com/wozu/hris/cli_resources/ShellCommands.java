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

    private String debug;

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
    private PerformanceService pfService;
    private TableDisplay tDisplay;

    public ShellCommands(AccountRepository aRepo, InputReader inputReader, ShellResult shellResult, AccountService aService, EmployeeService eService, RoleRepository rRepo, PasswordEncoder bCryptPasswordEncoder, PayrateService prService, DepartmentService dService, DepartmentEmployeeService dEService, PositionService pService, BenefitService bService, TrainingService tService, EmployeeTrainingService eTService, PerformanceService pfService){
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
        this.pfService = pfService;
        this.tDisplay = new TableDisplay();
    }

    public String getDebug(){
       return this.debug;
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

    public Account createAccount(String type){
        Account accountHolder = null;
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
        if(type.equalsIgnoreCase("Candidate")) {
            accountHolder = aService.registerCandidateAccount(newAccount, new Employee());
        }else{
            accountHolder = aService.registerEmployeeAccount(newAccount);
        }
        return accountHolder;
    }

    public boolean promoteAccount(String type, String t){
        return promoteAccount(type, t, null);
    }

    public boolean promoteAccount(String type, String t, String role){
        boolean result = false;
        Account targetAccount = type.equalsIgnoreCase("Username") ? aService.findByUsername(t).get() : aService.findAccountById(Long.parseLong(t));
        int targetPermission = getPermissionLevel(targetAccount.getRoles());

        if(targetPermission >= ERole.ROLE_HR.getID()){
            debug = "Permission Level Not High Enough!";
            return false;
        }

        if(role != null){
            if(role == "Employee"){
                result = aService.promoteCandidateAccount(targetAccount) != null ? true : false;
            }else if(role == "Manager"){
                result = aService.promoteEmployeeAccount(targetAccount) != null ? true : false;
            }
        }else{
            result = aService.promoteCandidateAccount(targetAccount) != null ? true : false;
        }

        if(!result){
            debug = "Account possibly already has role.";
        }

        return result;
    }

    public boolean promoteAccount(String type, String t, boolean hr){
        return promoteAccount(type, t, hr, null);
    }

    public boolean promoteAccount(String type, String t, boolean hr, String role){
        boolean result = false;
        Account targetAccount = type.equalsIgnoreCase("Username") ? aService.findByUsername(t).get() : aService.findAccountById(Long.parseLong(t));
        int targetPermission = getPermissionLevel(targetAccount.getRoles());

        if(targetPermission >= ERole.ROLE_HR.getID()){
            debug = "Permission Level Not High Enough!";
            return false;
        }

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
        if(!result){
            debug = "Account possibly already has role.";
        }
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
        if(permLvl == ERole.ROLE_CANDIDATE.getID()){
            return "Candidate";
        }else if(permLvl == ERole.ROLE_EMPLOYEE.getID()){
            return "Employee";
        }else if(permLvl == ERole.ROLE_MANAGER.getID()){
            return "Manager";
        }else{
            return "HR";
        }

    }

    public LinkedHashMap<String, Map<String, String>> getCommandGroup(int permLvl){
        LinkedHashMap<String, Map<String, String>> listedCommands = new LinkedHashMap<>();


        //Commands available to Candidate+ Roles
        if(permLvl >= ERole.ROLE_CANDIDATE.getID()){
            Map<String, String> commands = new HashMap<>();

            commands.put("signOut", "Sign out of session");
            //Role Restricted Commands
            if(permLvl == ERole.ROLE_CANDIDATE.getID()){
                commands.put("view", "View Company Information");
            }
            //Command Level
            listedCommands.put("Level 0", commands);
        }

        //Commands available to Employee+ Roles
        if(permLvl >= ERole.ROLE_EMPLOYEE.getID()){
            Map<String, String> commands = new HashMap<>();
            commands.put("cIn", "Clock In");
            commands.put("cOut", "Clock Out");
            commands.put("update", "Update Information");
            commands.put("info", "View Your Information");

            //Role Restricted Commands
            if(permLvl == ERole.ROLE_EMPLOYEE.getID()){

            }
            //Command Level
            listedCommands.put("Level 1", commands);
        }

        //Commands available to Manager+ Roles
        if(permLvl >= ERole.ROLE_MANAGER.getID()){
            Map<String, String> commands = new HashMap<>();

            commands.put("view-performance", "View performance review of Employee");

            //Role Restricted Commands
            if(permLvl == ERole.ROLE_MANAGER.getID()){
                commands.put("performance", "Add performance review to Employee");
            }
            //Command Level
            listedCommands.put("Level 2", commands);
        }

        //Commands available to HR Role
        if(permLvl >= ERole.ROLE_HR.getID()){
            Map<String, String> commands = new HashMap<>();
            commands.put("promote", "Promote Employee with Account");
            commands.put("edit", "Edit Employee");
            commands.put("deactivate", "Deactivate current Interface");
            commands.put("manage", "Manage Company");

            //Role Restricted Commands
            if(permLvl == ERole.ROLE_HR.getID()){

            }
            //Command Level
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
        do {
            if (item.equalsIgnoreCase("Date Of Birth")) {
                newValue = inputReader.prompt("Input Date Of Birth (mm/dd/yyyy)");
            } else {
                if (hr && !item.equals("First Name") && !item.equals("Last Name")) {
                    hSwitch = true;
                } else {
                    newValue = inputReader.prompt(String.format("Update %s", item));
                }
            }

            if (!hSwitch && !inputReader.confirmationPrompt(String.format("Confirm new %s: %s", item, newValue))) {

            }else {
                break;
            }
        }while(true);

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
                                if(inputReader.confirmationPrompt(String.format("New Hourly Rate: %.2f", Double.parseDouble(newValue)))){
                                    payrate.setHourlyRate(Double.parseDouble(newValue));
                                    break;
                                }
                            }else if(selection.equalsIgnoreCase("B")){
                                newValue = inputReader.prompt(attributes.get(selection));
                                if(inputReader.confirmationPrompt(String.format("New Salary: %.2f", Double.parseDouble(newValue)))){
                                    payrate.setSalary(Double.parseDouble(newValue));
                                    break;
                                }
                            }else if(selection.equalsIgnoreCase("C")){
                                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                                newValue = inputReader.prompt(String.format("%s (\"mm/dd/yyyy\")", attributes.get(selection)));
                                if(inputReader.confirmationPrompt(String.format("New Effective Date: %s", newValue))){
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

    public Boolean manageCompany(String type){
        clearConsole();
        Boolean result = false;
        String selection;
        Map<String, String> options = new HashMap<>();
        options.put("A", String.format("Add %s", type)); // Add Entity
        options.put("B", String.format("Remove %s", type)); // Remove Entity
        options.put("C", String.format("View all %ss", type)); // View All Entities
        if(!type.equalsIgnoreCase("Employee")) {
            options.put("D", String.format("Edit %s", type)); // Edit Existing Entity
        }
        options.put("X", "FINISH/CANCEL");

        do{

            selection = inputReader.listInput(type,
                    "Selection an action [] above.",
                    options,
                    true);

            if(selection.equalsIgnoreCase("X")){
                break;
            }

            if(selection.equalsIgnoreCase("A")){
                clearConsole();
                if(type.equalsIgnoreCase("Training") || type.equalsIgnoreCase("Benefit")){

                    String name = null;
                    String desc = null;

                    do{
                        shellResult.printInfo(String.format("Create new %s:", type));
                        String holder = inputReader.prompt(String.format("%s Name(input \"exit\" to cancel)", type));

                        if(holder.equalsIgnoreCase("exit")){
                            debug = "Cancelled Creation";
                            return false;
                        }

                        if(tService.existsByName(holder) || bService.existsByName(holder)){
                            clearConsole();
                            shellResult.printError(String.format("%s already Exists!", type));
                            continue;
                        }

                        if(inputReader.confirmationPrompt(String.format("%s Name: %s", type, holder))){
                            name = holder;
                            break;
                        }
                    }while(name == null);

                    clearConsole();

                    do{
                        shellResult.printInfo(String.format("Create new %s:", type));
                        String holder = inputReader.prompt(String.format("%s Description(input \"exit\" to cancel)", type));

                        if(holder.equalsIgnoreCase("exit")){
                            debug = "Cancelled Creation";
                            return false;
                        }

                        if(inputReader.confirmationPrompt(String.format("%s Description: %s", type, holder))){
                            desc = holder;
                            break;
                        }


                    }while(desc == null);

                    if(type.equalsIgnoreCase("Training")){
                        clearConsole();
                        tService.createTraining(new Training(name, desc));
                        result = true;
                        shellResult.printSuccess("Created Successfully!");
                    }else if(type.equalsIgnoreCase("Benefit")){
                        clearConsole();
                        bService.createBenefit(new Benefit(name, desc));
                        result = true;
                        shellResult.printSuccess("Created Successfully!");
                    }

                }else if(!type.equalsIgnoreCase("Employee")){

                    String name = null;

                    do{
                        shellResult.printInfo(String.format("Create new %s:", type));
                        String holder = inputReader.prompt(String.format("%s Name(input \"exit\" to cancel)", type));

                        if(holder.equalsIgnoreCase("exit")){
                            debug = "Cancelled Creation";
                            return false;
                        }

                        if(dService.existsByName(holder) || pService.existsByName(holder)){
                            clearConsole();
                            shellResult.printError(String.format("%s already Exists!", type));
                            continue;
                        }

                        if(inputReader.confirmationPrompt(String.format("%s Name: %s", type, holder))){
                            name = holder;
                            break;
                        }
                    }while(name == null);

                    if(type.equalsIgnoreCase("Department")){
                        clearConsole();
                        dService.createDepartment(new Department(name));
                        result = true;
                        shellResult.printSuccess("Created Successfully!");
                    }else if(type.equalsIgnoreCase("Position")){
                        clearConsole();
                        pService.createPosition(new Position(name));
                        result = true;
                        shellResult.printSuccess("Created Successfully!");
                    }

                }else{
                   clearConsole();
                   if(createAccount("Employee") != null){
                       clearConsole();
                        result = true;
                        shellResult.printSuccess("Created Successfully!");
                   }else{
                       debug = "Cancelled Creation";
                   }
                }
            }else if(selection.equalsIgnoreCase("B")){
                Long id = null;
                do{
                    String holder = inputReader.prompt(String.format("%s ID", type));

                    try{
                        id = Long.parseLong(holder);
                    }catch(NumberFormatException e){
                        clearConsole();
                        shellResult.printError("Error Parsing Input! Must be Digits!");
                        continue;
                    }

                    if(type.equalsIgnoreCase("Position")){
                        if(pService.existsById(id)){
                            pService.deletePosition(id);
                            clearConsole();
                            shellResult.printSuccess(String.format("Removed %s Successfully!", type));
                            result = true;
                        }else{
                            shellResult.printError(String.format("No %s With ID %s", type, id));
                        }
                    }else if(type.equalsIgnoreCase("Benefit")){
                        if(bService.existsById(id)){
                            bService.deleteBenefit(id);
                            clearConsole();
                            shellResult.printSuccess(String.format("Removed %s Successfully!", type));
                            result = true;
                        }else{
                            shellResult.printError(String.format("No %s With ID %s", type, id));
                        }
                    }else if(type.equalsIgnoreCase("Department")){
                        if(dService.existsById(id)){
                            dService.deleteDepartment(id);
                            clearConsole();
                            shellResult.printSuccess(String.format("Removed %s Successfully!", type));
                            result = true;
                        }else{
                            shellResult.printError(String.format("No %s With ID %s", type, id));
                        }
                    }else if(type.equalsIgnoreCase("Training")){
                        if(tService.existsById(id)){
                            tService.deleteTraining(id);
                            clearConsole();
                            shellResult.printSuccess(String.format("Removed %s Successfully!", type));
                            result = true;
                        }else{
                            shellResult.printError(String.format("No %s With ID %s", type, id));
                        }
                    }else if(type.equalsIgnoreCase("Employee")){
                        if(eService.existsById(id)){
                            clearConsole();
                            eService.deleteEmployee(id);
                            shellResult.printSuccess(String.format("Removed %s Successfully!", type));
                            result = true;
                        }else{
                            shellResult.printError(String.format("No %s With ID %s", type, id));
                        }
                    }
                }while(id == null);
            }else if(selection.equalsIgnoreCase("C")){

            }else if(selection.equalsIgnoreCase("D")){
                if(type.equalsIgnoreCase("Department")){
                    clearConsole();
                    Long dId = null;
                    Department targetD = null;
                    Account target = null;
                    do{
                        try{
                            dId = Long.parseLong(inputReader.prompt("Department ID"));
                            if(dService.existsById(dId)){
                                targetD = dService.findDepartmentById(dId);
                                if(inputReader.confirmationPrompt(String.format("Target Department %s", targetD.getName()))){
                                    break;
                                }else{
                                    targetD = null;
                                }
                            }else{
                                shellResult.printError("Invalid ID (ID does not exist)");
                            }
                        }catch(NumberFormatException e){
                            shellResult.printError("Invalid Input");
                            dId = null;
                            result = false;
                            debug = e.toString();
                        }
                    }while(targetD == null);

                    Map<String, String> departmentOptions = new HashMap<>();
                    departmentOptions.put("A", "Name");
                    departmentOptions.put("B", "Manager");
                    departmentOptions.put("X", "FINISH/CANCEL");

                    String departmentSelection = null;

                    do{
                        departmentSelection = inputReader.listInput("Edit Department",
                                "Please select an option [] above.",
                                departmentOptions,
                                true);

                    }while(departmentSelection == null);

                    if(departmentSelection.equalsIgnoreCase("X")){
                        clearConsole();
                        debug = "Operation Cancelled";
                       return result;
                    }

                    if(departmentSelection.equalsIgnoreCase("A")){
                        do{
                            clearConsole();
                            String newName = inputReader.prompt("New Department Name(input \"exit\" to cancel)");
                            if(newName.equalsIgnoreCase("exit")){
                                break;
                            }

                            if(inputReader.confirmationPrompt(String.format("New Name %s", newName))){
                                result = true;
                                clearConsole();
                                shellResult.printSuccess("Changed Department Name Successfully!");
                                targetD.setName(newName);
                                dService.updateDepartemnts(targetD.getId(), targetD);
                                break;
                            }
                        }while(true);
                    }else if(departmentSelection.equalsIgnoreCase("B")) {
                        Map<String, String> targetOptions = new HashMap<>();
                        targetOptions.put("A", "Account ID");
                        targetOptions.put("B", "Account Username");
                        targetOptions.put("C", "Employee ID");
                        targetOptions.put("X", "FINISH/CANCEL");
                        String targetSelection;
                        clearConsole();
                        do {

                            String t;
                            targetSelection = inputReader.listInput("New Manager",
                                    "Please select an option [] above.",
                                    targetOptions,
                                    true);

                            if (targetSelection.equalsIgnoreCase("X")) {
                                clearConsole();
                                debug = "Operation Cancelled";
                                target = null;
                                break;
                            }

                            t = inputReader.prompt(targetOptions.get(targetSelection));
                            if (targetSelection.equalsIgnoreCase("A")) {
                                if (aService.existsById(Long.parseLong(t))) {
                                    target = aService.findAccountById(Long.parseLong(t));
                                } else {
                                    shellResult.printError("Invalid ID");
                                }
                            }else if(targetSelection.equalsIgnoreCase("B")){
                                if(aService.findByUsername(t) != null){
                                    target = aService.findByUsername(t).get();
                                }else{
                                    clearConsole();
                                    shellResult.printError("Account with Username does not exist!");
                                }
                            }else if(targetSelection.equalsIgnoreCase("C")){
                                if (eService.existsById(Long.parseLong(t))) {
                                    target = eService.findEmployee(Long.parseLong(t)).getAccount();
                                } else {
                                    shellResult.printError("Invalid ID");
                                }
                            }

                            if (getPermissionLevel(target.getRoles()) < ERole.ROLE_MANAGER.getID()) {
                                clearConsole();
                                shellResult.printError("New Manager Must Be A \"Manager\" or above!");
                                continue;
                            } else if (dService.findByManagerId(target.getEmployee()) != null) {
                                clearConsole();
                                shellResult.printError("Employee Already Manages A Department!");
                                continue;
                            }

                            if(target != null) {
                                if (inputReader.confirmationPrompt(String.format("New Manager %s %s", target.getEmployee().getFirstName(), target.getEmployee().getLastName()))) {
                                    clearConsole();
                                    result = true;
                                    shellResult.printSuccess("Updated Department Manager Successfully!");
                                    targetD.setManager(target.getEmployee());
                                    dService.updateDepartemnts(targetD.getId(), targetD);
                                    break;
                                }
                            }
                        } while(true);
                    }
                }else if(type.equalsIgnoreCase("Benefit")){
                    Map<String, String> option = new HashMap<>();
                    option.put("A", "Name");
                    option.put("B", "Description");
                    option.put("X", "FINISH/CANCEL");
                    Benefit target = null;
                    Long id = null;
                    String benefitSelection = null;

                    do{
                        String holder = inputReader.prompt("Benefit ID");
                        try{
                            id = Long.parseLong(holder);
                            if(bService.existsById(id)){
                                target = bService.findBenefit(id);
                                if(inputReader.confirmationPrompt(String.format("Edit Benefit %s", target.getName()))){
                                    break;
                                }else{
                                    target = null;
                                    id = null;
                                    continue;
                                }
                            }else{
                                shellResult.printError("ID does not exist!");
                                id = null;
                                continue;
                            }
                        }catch(NumberFormatException e){
                            shellResult.printError("Invalid Input");
                            id = null;
                            result = false;
                            debug = e.toString();
                        }
                    }while(id == null);

                    do{
                        benefitSelection = inputReader.listInput("Benefit",
                                "Please select an option [] above.",
                                option,
                                true);
                        if(benefitSelection.equalsIgnoreCase("X")){
                            debug = "Operation Cancelled!";
                            break;
                        }

                        if(benefitSelection.equalsIgnoreCase("A")){
                            String holder = inputReader.prompt("New Name(input \"exit\" to cancel)");
                            if(holder.equalsIgnoreCase("exit")){
                                continue;
                            }
                            if(inputReader.confirmationPrompt(String.format("New Name %s", holder))){
                                clearConsole();
                                result = true;
                                shellResult.printSuccess("Changed Name Successfully!");
                                target.setName(holder);
                                bService.updateBenefit(target.getId(), target);
                                break;
                            }
                        }else if(benefitSelection.equalsIgnoreCase("B")){
                            String holder = inputReader.prompt("New Description(input \"exit\" to cancel)");
                            if(holder.equalsIgnoreCase("exit")){
                                continue;
                            }
                            if(inputReader.confirmationPrompt(String.format("New Description %s", holder))){
                                clearConsole();
                                result = true;
                                shellResult.printSuccess("Changed Description Successfully!");
                                target.setDescription(holder);
                                bService.updateBenefit(target.getId(), target);
                                break;
                            }
                        }

                    }while(true);
                }else if(type.equalsIgnoreCase("Position")){
                    Long id;
                    Position target;
                    String newName;

                    do {
                        String holder = inputReader.prompt("Position ID(input \"exit\" to cancel)");
                        if (!holder.equalsIgnoreCase("exit")) {
                            try{
                                id = Long.parseLong(holder);
                                if(pService.existsById(id)){
                                    target = pService.findAccountById(id);
                                    if(inputReader.confirmationPrompt(String.format("Edit %s", target.getName()))){
                                        do{
                                            newName = inputReader.prompt("New Name");
                                            if(inputReader.confirmationPrompt(String.format("New Name %s", newName))){
                                                break;
                                            }
                                        }while(true);
                                        clearConsole();
                                        shellResult.printSuccess("Changed Name Successfully!");
                                        target.setName(newName);
                                        pService.updateDepartments(target.getId(), target);
                                        break;
                                    }else{
                                        id = null;
                                        target = null;
                                        continue;
                                    }
                                }else{
                                    debug = "ID does not exist";
                                    shellResult.printError("ID Does Not Exist");
                                    continue;
                                }
                            }catch(NumberFormatException e){
                                debug = "Invalid Input";
                                shellResult.printError("Invalid Input");
                                continue;
                            }
                        }
                    }while(true);
                }else if(type.equalsIgnoreCase("Training")){
                    Map<String, String> trainingOptions = new HashMap<>();
                    trainingOptions.put("A", "Name");
                    trainingOptions.put("B", "Description");
                    trainingOptions.put("X", "FINISH/CANCEL");
                    Training target = null;
                    Long id = null;

                    do{
                        String holder = inputReader.prompt("Training ID");
                        try{
                            id = Long.parseLong(holder);
                            if(tService.existsById(id)){
                                target = tService.findTraining(id);
                                if(inputReader.confirmationPrompt(String.format("Edit %s", target.getTrainingName()))){
                                    break;
                                }
                            }else{
                                shellResult.printError("ID Does Not Exist!");
                                continue;
                            }
                        }catch(Exception e){
                            shellResult.printError("Invalid Input");
                            continue;
                        }

                    }while(true);
                    clearConsole();
                    do{
                        String trainingSelection = inputReader.listInput("Training",
                                "Please select an option [] above",
                                trainingOptions,
                                true);

                        if(trainingSelection.equalsIgnoreCase("X")){
                            debug = "Operation Cancelled";
                            break;
                        }

                        String newValue = inputReader.prompt(String.format("New %s", trainingOptions.get(trainingSelection)));
                        if(!inputReader.confirmationPrompt(String.format("New %s", newValue))){
                            continue;
                        }
                        if(trainingSelection.equalsIgnoreCase("A")){
                            clearConsole();
                            shellResult.printSuccess("Updated Name Successfully!");
                            result = true;
                            target.setTrainingName(newValue);
                            tService.updateTraining(target.getId(), target);
                            break;
                        }else if(trainingSelection.equalsIgnoreCase("B")){
                            clearConsole();
                            shellResult.printSuccess("Updated Description Successfully!");
                            result = true;
                            target.setDescription(newValue);
                            tService.updateTraining(target.getId(), target);
                            break;
                        }
                    }while(true);
                }
            }
        }while(true);

        return result;
    }

    public boolean makePerformance(Account currentUser){
        clearConsole();
        shellResult.printInfo("Performance Review:");
        String comment = null;
        Employee reviewee = null;
        Employee reviewer = currentUser.getEmployee();
        Department managingDepartment = dService.findByManagerId(reviewer);

        do{
            String holder = inputReader.prompt("Comment(input \"exit\" to cancel)");
            if(holder.equalsIgnoreCase("exit")){
                return false;
            }
            if(inputReader.confirmationPrompt("Finish Comment")){
                comment = holder;
                break;
            }
        }while(comment == null);
        clearConsole();
        do{
            Employee target = null;
            Map<String, String> typeOptions = new HashMap<>();
            typeOptions.put("A", "Account ID");
            typeOptions.put("B", "Account Username");
            typeOptions.put("C", "Employee ID");
            typeOptions.put("X", "Cancel");

            String type = inputReader.listInput("Find by: ",
                    "Please selection an option [] above",
                    typeOptions,
                    true);

            if(type.equalsIgnoreCase("X")){
                return false;
            }

            String t = inputReader.prompt(typeOptions.get(type));

            if(type.equals("A")){
                target = aService.findAccountById(Long.parseLong(t)).getEmployee();
            }else if(type.equals("B")){
                target = aService.findByUsername(t).get().getEmployee();
            }else if(type.equals("C")){
                target = eService.findEmployee(Long.parseLong(t));
            }

            if(dEService.existsByEmployeeAndDepartment(target, managingDepartment) && getPermissionLevel(target.getAccount().getRoles()) < ERole.ROLE_MANAGER.getID()){
                reviewee = target;
                break;
            }else{
                shellResult.printError("Cannot Add A Performance Review To Employee!");
            }

        }while(reviewee == null);

        pfService.createPerformance(new Performance(comment, reviewer, reviewee));
        return true;
    }


}