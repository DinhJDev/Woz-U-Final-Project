package com.wozu.hris;

import com.wozu.hris.cli_resources.CustomPromptProvider;
import com.wozu.hris.cli_resources.InputReader;
import com.wozu.hris.cli_resources.ShellCommands;
import com.wozu.hris.cli_resources.ShellResult;
import com.wozu.hris.models.Account;
import com.wozu.hris.models.ERole;
import com.wozu.hris.models.Employee;
import com.wozu.hris.services.AccountService;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.LogManager;

@SpringBootApplication
public class HrisApplication {

	@Autowired
	public static ShellResult shellResult;

	@Autowired
	public static InputReader inputReader;

	private static Account currentUser = null;
	private static boolean active = true;

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

}

@ShellComponent
@ShellCommandGroup("Basic")
class BasicCommands{

	@Autowired
	ShellResult shellResult;

	@Autowired
	InputReader inputReader;

	@Autowired
	AccountService aService;

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
						CustomPromptProvider.changePrompt("connected");
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
						CustomPromptProvider.changePrompt("connected");
						shellCommands.getCommandGroup(HrisApplication.getCurrentUser().getRoles());
					}
				}
			}
		}

}

@ShellComponent
@ShellCommandGroup("Candidate")
class CandidateCommands{

	public Availability candidateAvailability(){
		boolean connected = false;
		return connected ? Availability.available() : Availability.unavailable("Not connected");
	}

	@Autowired
	ShellResult shellResult;

	@Autowired
	InputReader inputReader;

	@Autowired
	AccountService aService;

	@Autowired
	ShellCommands shellCommands;

	@ShellMethod("Candidate Stuff")
	@ShellMethodAvailability("candidateAvailability")
	public String candidateMethod(){
		return shellResult.getSuccessMessage("I'm a Candidate!");
	}
}

@ShellComponent
@ShellCommandGroup("Employee")
class EmployeeCommands {

	public Availability employeeAvailability(){
		boolean connected = true;
		return connected ? Availability.available() : Availability.unavailable("Not connected");
	}

	@Autowired
	ShellResult shellResult;

	@Autowired
	InputReader inputReader;

	@Autowired
	AccountService aService;

	@Autowired
	ShellCommands shellCommands;

	@ShellMethod("Test result output")
	@ShellMethodAvailability("employeeAvailability")
	public String helloWorld(@ShellOption(value = "Name", defaultValue = "Woz U") String optional){
		shellCommands.clearConsole();
		return shellResult.getSuccessMessage(String.format("Hello World! - %s", optional));
	}

	@ShellMethod("Test printBanner method")
	@ShellMethodAvailability("employeeAvailability")
	public void seeBanner(){
		shellCommands.clearConsole();
		shellCommands.displayBanner();
	}

	@ShellMethod("Clock in")
	@ShellMethodAvailability("employeeAvailability")
	public void clockIn(){

	}
}

@ShellComponent
@ShellCommandGroup("Manager")
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
		boolean connected = true;
		return connected ? Availability.available() : Availability.unavailable("Not connected");
	}

}

@ShellComponent
@ShellCommandGroup("HR")
class HRCommands{

	@Autowired
	ShellResult shellResult;

	@Autowired
	InputReader inputReader;

	@Autowired
	AccountService aService;

	public Availability hrAvailability(){
		boolean connected = true;
		return connected ? Availability.available() : Availability.unavailable("Not connected");
	}

	@ShellMethod("Deactivate HRIS")
	@ShellMethodAvailability("hrAvailability")
	public void deactivate(){
		HrisApplication.deactivate();
	}

}