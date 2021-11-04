package com.wozu.hris;

import com.wozu.hris.cli_resources.ShellCommands;
import com.wozu.hris.cli_resources.ShellResult;
import com.wozu.hris.models.Account;
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

import java.util.logging.LogManager;

@SpringBootApplication
public class HrisApplication {

	private static Account currentUser = null;
	private static boolean active = true;

	public static void main(String[] args) {
		//SpringApplication.run(HrisApplication.class, args);
		SpringApplication app = new SpringApplication(HrisApplication.class);
		while(active) {
			if(currentUser == null) {

				app.run(args);
			}
		}

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

}

@ShellComponent
@ShellCommandGroup("Basic")
class BasicCommands{

	@ShellMethodAvailability("signOut")
	public Availability currentSessionOut(){
		return HrisApplication.getSession() ? Availability.available() : Availability.unavailable("Session not in Progress");
	}

	@ShellMethodAvailability("signIn")
	public Availability currentSessionIn(){
		return HrisApplication.getSession() ? Availability.unavailable("Session in Progress") : Availability.available();
	}

	@ShellMethod("Sign Out Of Session")
	public void signOut(){

	}

	@ShellMethod("Sign Into Session")
	public void signIn(){

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

	@ShellMethod("Test result output")
	@ShellMethodAvailability("employeeAvailability")
	public String helloWorld(@ShellOption(value = "Name", defaultValue = "Woz U") String optional){
		ShellCommands.clearConsole();
		return shellResult.getSuccessMessage(String.format("Hello World! - %s", optional));
	}

	@ShellMethod("Test printBanner method")
	@ShellMethodAvailability("employeeAvailability")
	public void seeBanner(){
		ShellCommands.clearConsole();
		ShellCommands.displayBanner();
	}

	@ShellMethod("Clock in")
	@ShellMethodAvailability("employeeAvailability")
	public void clockIn(){

	}
}

@ShellComponent
@ShellCommandGroup
class HRCommands{

	public Availability hrAvailability(){
		boolean connected = true;
		return connected ? Availability.available() : Availability.unavailable("Not connected");
	}

	@ShellMethod
	@ShellMethodAvailability("hrAvailability")
	public void deactivate(){
		HrisApplication.deactivate();
	}

}