package com.wozu.hris;

import com.wozu.hris.cli_resources.CustomPromptProvider;
import com.wozu.hris.cli_resources.InputReader;
import com.wozu.hris.cli_resources.ShellCommands;
import com.wozu.hris.cli_resources.ShellResult;
import com.wozu.hris.models.*;
import com.wozu.hris.repositories.RoleRepository;
import com.wozu.hris.repositories.TimesheetRepository;
import com.wozu.hris.services.AccountService;
import com.wozu.hris.services.TimesheetService;
import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.*;
import org.springframework.boot.Banner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.logging.LogManager;

@SpringBootApplication
public class HrisApplication {

	private static Account currentUser = null;
	private static boolean active = true;
	private static LinkedHashMap<String, Map<String, String>> list = null;

	public static void main(String[] args) {
		// Creating Console Clearing Process
		try {
			if (System.getProperty("os.name").contains("Windows"))
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			else
				Runtime.getRuntime().exec("clear");
		} catch (IOException | InterruptedException ex) {}

		SpringApplication app = new SpringApplication(HrisApplication.class);
		app.setLogStartupInfo(false);
		app.run(args);

	}

	public static void deactivate(){

		active = false;
		System.exit(1);
	}

	public static boolean getSession(){
		if(currentUser != null){
			return true;
		}
		return false;
	}

	public static void outSession(){
		if(currentUser != null){
			currentUser = null;
		}
	}

	public static Account getCurrentUser(){
		return currentUser;
	}

	public static void setCurrentUser(Account acc){
		currentUser = acc;
	}

	public static void setCommandList(LinkedHashMap<String, Map<String, String>> l){
		list = l;
	}

	public static LinkedHashMap<String, Map<String, String>> getCommandList(){
		return list;
	}
}

@ShellComponent
@ShellCommandGroup("Basic")
@Transactional
class BasicCommands{

	@Autowired
	ShellResult shellResult;
	@Autowired
	InputReader inputReader;
	@Autowired
	AccountService aService;
	@Autowired
	RoleRepository rRepo;
	@Autowired
	ShellCommands shellCommands;

	@ShellMethodAvailability("signOut")
	public Availability currentSessionOut(){
		return HrisApplication.getSession() ? Availability.available() : Availability.unavailable("Session not in Progress");
	}

	@ShellMethodAvailability("connect")
	public Availability currentSessionIn(){
		return HrisApplication.getSession() ? Availability.unavailable("Session in Progress") : Availability.available();
	}

	@ShellMethodAvailability("currentSessionOut")
	@ShellMethod("Sign Out Of Session")
	public void signOut(){
		HrisApplication.setCurrentUser(null);
		HrisApplication.setCommandList(null);
		shellCommands.clearConsole();
		CustomPromptProvider.changePrompt("disconnected");
	}

	@ShellMethodAvailability("currentSessionIn")
	@ShellMethod(key={"c", "connect", "-c", "sign-in", "in"}, value="Connect to HRIS")
	public void connect(){
			shellCommands.clearConsole();
			if(HrisApplication.getCurrentUser() == null) {
				//Optional<Account> possibleUser;
				Account possibleUser;
				Map<String, String> options = new HashMap<>();
				options.put("A", "Sign In");
				options.put("B", "Register");
				String selection = inputReader.listInput(
						"Welcome to McMilan and Associates HRIS",
						"Please select an option [] provided above.",
						options, // HashMap
						true);
				if(selection.equalsIgnoreCase("A")){
					shellResult.printSuccess("Sign In to Account");
					String username = inputReader.prompt("Username");
					String password = inputReader.prompt("Password", null, false);
					possibleUser = aService.authenticate( new Account(username, password));
					if(possibleUser != null){
						shellResult.printSuccess("Successfully Logged In!");
						HrisApplication.setCurrentUser(possibleUser);
						HrisApplication.setCommandList(shellCommands.getCommandGroup(shellCommands.getPermissionLevel(possibleUser.getRoles())));
						CustomPromptProvider.changePrompt("connected");
						shellCommands.clearConsole();
						shellCommands.displayBanner();
						shellResult.printList("Commands", HrisApplication.getCommandList());
					}else{
						shellResult.printError("Error, try again!");
						connect();
					}
				}else if(selection.equalsIgnoreCase("B")){
					shellResult.printSuccess("Register Account");
					Account user = shellCommands.createAccount();
					if(user == null){
						connect();
					}else{
						shellResult.printSuccess("Successfully Registered Candidate Account!");
						HrisApplication.setCurrentUser(user);
						HrisApplication.setCommandList(shellCommands.getCommandGroup(shellCommands.getPermissionLevel(user.getRoles())));
						CustomPromptProvider.changePrompt("connected");
						shellCommands.clearConsole();
						shellCommands.displayBanner();
						shellResult.printList("Commands", HrisApplication.getCommandList());
					}
				}
			}
		}

}

@ShellComponent
@ShellCommandGroup("Candidate")
@Transactional
class CandidateCommands{

	@Autowired
	ShellResult shellResult;

	@Autowired
	InputReader inputReader;

	@Autowired
	AccountService aService;

	@Autowired
	ShellCommands shellCommands;

	public Availability candidateAvailability(){

		Account authUser = aService.findAccountById(HrisApplication.getCurrentUser().getId());

		if(authUser == null){
			return Availability.unavailable("Not Connected!");
		}

		if(shellCommands.getPermissionLevel(authUser.getRoles()) >= ERole.ROLE_CANDIDATE.getID()){
			return HrisApplication.getSession() ? Availability.available() : Availability.unavailable("Not Connected");
		}

		return Availability.unavailable("Invalid Permission Level");
	}

	@ShellMethod("Candidate Stuff")
	@ShellMethodAvailability("candidateAvailability")
	public String candidateMethod(){
		return shellResult.getSuccessMessage("I'm a Candidate!");
	}
}

@ShellComponent
@ShellCommandGroup("Employee")
@Transactional
class EmployeeCommands {

	public Availability employeeAvailability(){
		Account authUser = aService.findAccountById(HrisApplication.getCurrentUser().getId());

		if(authUser == null){
			return Availability.unavailable("Not Connected!");
		}

		if(shellCommands.getPermissionLevel(authUser.getRoles()) >= ERole.ROLE_EMPLOYEE.getID()){
			return HrisApplication.getSession() ? Availability.available() : Availability.unavailable("Not Connected");
		}

		return Availability.unavailable("Invalid Permission Level");
	}

	@Autowired
	ShellResult shellResult;

	@Autowired
	InputReader inputReader;

	@Autowired
	AccountService aService;

	@Autowired
	ShellCommands shellCommands;

	@Autowired
	TimesheetService tService;

	@Autowired
	TimesheetRepository tRepo;

	@ShellMethod(key="cIn", value="Clock in")
	@ShellMethodAvailability("employeeAvailability")
	public void clockIn(){
		Account currentUser = HrisApplication.getCurrentUser();
		Employee currentEmp = currentUser.getEmployee();

		if(!currentEmp.getClockedIn()){
			Timesheet timesheet = new Timesheet();
			timesheet.setStart(new Date());
			timesheet.setEmployee(currentEmp);
			tService.createTimesheet(timesheet);

			currentEmp.setClockedIn(true);

			shellCommands.clearConsole();
			shellResult.printSuccess("Clocked in Successfully");
			shellCommands.displayBanner();
			shellResult.printList("Commands", HrisApplication.getCommandList());

		}else{
			shellResult.printError("Current User is already clocked in!");
		}
	}

	@ShellMethod(key="cOut", value="Clock out")
	@ShellMethodAvailability("employeeAvailability")
	public void clockOut(){
		Account currentUser = HrisApplication.getCurrentUser();
		Employee currentEmp = currentUser.getEmployee();

		if(currentEmp.getClockedIn()){
			Timesheet timesheet = tRepo.findTopByEmployeeOrderByIdDesc(currentEmp);

			if(timesheet != null){
				timesheet.setEnd(new Date());
				currentEmp.setClockedIn(false);

				shellCommands.clearConsole();
				shellResult.printSuccess("Clocked out Successfully!");
				shellCommands.displayBanner();
				shellResult.printList("Commands", HrisApplication.getCommandList());
			}else{
				shellResult.printError("Error finding latest Timesheet");
			}
		}else{
			shellResult.printError("Current User is not clocked in!");

		}
	}
}

@ShellComponent
@ShellCommandGroup("Manager")
@Transactional
class ManagerCommands{

	@Autowired
	ShellResult shellResult;

	@Autowired
	InputReader inputReader;

	@Autowired
	AccountService aService;

	@Autowired
	ShellCommands shellCommands;

	public Availability ManagerAvailability(){
		Account authUser = aService.findAccountById(HrisApplication.getCurrentUser().getId());

		if(authUser == null){
			return Availability.unavailable("Not Connected!");
		}

		if(shellCommands.getPermissionLevel(authUser.getRoles()) >= ERole.ROLE_MANAGER.getID()){
			return HrisApplication.getSession() ? Availability.available() : Availability.unavailable("Not Connected");
		}

		return Availability.unavailable("Invalid Permission Level");
	}

	@ShellMethod(key="promote", value="Promote Candidate Account to Employee")
	public void promoCandidate(){
		shellResult.printInfo("Promote Candidate Account");
		String type = null;
		String target = null;
		Map<String, String> options = new HashMap<>();
		options.put("A", "Username");
		options.put("B", "Account ID");
		do{
			type = inputReader.listInput("Find by",
					"Please select an option [] provided above.",
					options,
					true);
		}while(type == null);

		do{
			target = inputReader.prompt(String.format("Input %s", type));
		}while(target == null);

		if(shellCommands.promoteAccount(type, target)){
			shellResult.printSuccess("Account Promoted Successfully!");
		}else{
			shellResult.printError("Failed to Promote Account!");
		}
	}

}

@ShellComponent
@ShellCommandGroup("HR")
@Transactional
class HRCommands{

	@Autowired
	ShellResult shellResult;

	@Autowired
	InputReader inputReader;

	@Autowired
	AccountService aService;

	@Autowired
	ShellCommands shellCommands;

	public Availability hrAvailability(){
		Account authUser = aService.findAccountById(HrisApplication.getCurrentUser().getId());

		if(authUser == null){
			return Availability.unavailable("Not Connected!");
		}

		if(shellCommands.getPermissionLevel(authUser.getRoles()) >= ERole.ROLE_HR.getID()){
			return HrisApplication.getSession() ? Availability.available() : Availability.unavailable("Not Connected");
		}

		return Availability.unavailable("Invalid Permission Level");
	}

	@ShellMethod("Deactivate CLI")
	@ShellMethodAvailability("hrAvailability")
	public void deactivate(){
		HrisApplication.deactivate();
	}

	@ShellMethod(key="hPromote", value="Advanced Promotion")
	@ShellMethodAvailability("hrAvailability")
	public void hPromote(){
		shellResult.printInfo("Promote Account");
		String type = null;
		String target = null;
		String role = null;
		Map<String, String> options = new HashMap<>();
		Map<String, String> roles = new HashMap<>();
		options.put("A", "Username");
		options.put("B", "Account ID");

		roles.put("A", "Employee");
		roles.put("B", "Manager");

		do{
			type = inputReader.listInput("Find by",
					"Please select an option [] provided above.",
					options,
					true);
		}while(type == null);

		do{
			target = inputReader.prompt(String.format("Input %s", type));
		}while(target == null);

		do{
			role = inputReader.listInput("New Role",
					"Please select an option [] provided above.",
					roles,
					true);
		}while(role == null);

		if(shellCommands.promoteAccount(type, target, role)){
			shellResult.printSuccess("Account Promoted Successfully!");
		}else{
			shellResult.printError("Failed to Promote Account!");
		}
	}

}