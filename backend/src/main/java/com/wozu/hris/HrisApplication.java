package com.wozu.hris;

import com.wozu.hris.cli_resources.ShellCommands;
import com.wozu.hris.cli_resources.ShellResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.boot.Banner;

@SpringBootApplication
public class HrisApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrisApplication.class, args);

	}

}

@ShellComponent
class EmployeeCommands {

	@Autowired
	ShellResult shellResult;

	@ShellMethod("Test result output")
	public String helloWorld(@ShellOption(value = "Name", defaultValue = "Woz U") String optional){
		ShellCommands.clearConsole();
		return shellResult.getSuccessMessage(String.format("Hello World! - %s", optional));
	}

	@ShellMethod("Test printBanner method")
	public void seeBanner(){
		ShellCommands.clearConsole();
		ShellCommands.displayBanner();
	}
}