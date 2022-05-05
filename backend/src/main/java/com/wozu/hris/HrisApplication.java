package com.wozu.hris;

import com.wozu.hris.cli_resources.*;
import com.wozu.hris.models.*;
import com.wozu.hris.repositories.AccountRepository;
import com.wozu.hris.repositories.RoleRepository;
import com.wozu.hris.repositories.TimesheetRepository;
import com.wozu.hris.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.*;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Supplier;

@SpringBootApplication
public class HrisApplication {

	// Development Switch, allows commands that bypass checks.
	private static final boolean developing = true;

	private static Account currentUser = null;
	private static boolean active = true;
	private static LinkedHashMap<String, Map<String, String>> list = null;
	private static int permissionLevel = 0;
	private static String stringRole = "";

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

	public static void setPermissionLevel(int i){permissionLevel = i;}

	public static int getPermissionLevel(){return permissionLevel;}

	public static boolean getDeveloping(){
		return developing;
	}

	public static void setStringRole(String role){stringRole = role;}

	public static String getStringRole(){return stringRole;}
}

@ShellComponent
@ShellCommandGroup("Development Commands")
@Transactional
class DevelopmentCommands{

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
	@Autowired
	EmployeeService eService;
	@Autowired
	TimesheetRepository tRepo;
	@Autowired
	PasswordEncoder bCryptPasswordEncoder;
	@Autowired
	AccountRepository aRepo;

	public Availability developmentCheck(){
		return HrisApplication.getDeveloping() ? Availability.available() : Availability.unavailable("Not in Development Mode");
	}

	@ShellMethodAvailability("developmentCheck")
	@ShellMethod(key="-pca", value="Promote Current Account")
	public void promoteCurrentAccount(){
		shellResult.printInfo("Promote Account");
		String type = "id";
		String target = HrisApplication.getCurrentUser().getId().toString();
		String role = null;

		Map<String, String> roles = new HashMap<>();

		roles.put("A", "Employee");
		roles.put("B", "Manager");
		roles.put("C", "HR");

		do{
			role = roles.get(inputReader.listInput("New Role",
					"Please select an option [] provided above.",
					roles,
					true));
		}while(role == null);

		if(shellCommands.promoteAccount(type, target, HrisApplication.getDeveloping(), role)){
			shellResult.printSuccess("Account Promoted Successfully!");
		}else{
			shellResult.printError("Failed to Promote Account!");
		}
	}

	@ShellMethodAvailability("developmentCheck")
	@ShellMethod(key="-oco", value="Override Clock Out")
	public void overrideClockOut(){
		Account currentUser = HrisApplication.getCurrentUser();
		Employee currentEmp = currentUser.getEmployee();

		currentEmp.setClockedIn(false);
		eService.updateEmployee(currentEmp.getId(), currentEmp);

	}

	@ShellMethodAvailability("developmentCheck")
	@ShellMethod(key="-ftt", value="Find top two timesheets")
	public void findTop2(String d) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Date chosenDate = format.parse(d);
		List<Timesheet> t = tRepo.findAllByEndBetweenAndEmployeeOrderByIdAsc( chosenDate, new Date(), HrisApplication.getCurrentUser().getEmployee());
		for(Timesheet item : t){
			System.out.println(item.getId());
		}
	}

	@ShellMethodAvailability("developmentCheck")
	@ShellMethod(key="-ora", value="Override Account by ID")
	public void overrideAccount(Long id){
		Account targetAccount = aService.findAccountById(id);
		String newValue = inputReader.prompt("Override Password");
		if(newValue.length() > 5){
			targetAccount.setPassword(bCryptPasswordEncoder.encode(newValue));
			aRepo.save(targetAccount);
			shellResult.printSuccess("Account Override Successful!");
		}else{
			shellResult.printError("Password must be greater than 5 Characters!");
		}
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
	@ShellMethod(key="signOut", value="Sign Out Of Session")
	public void signOut(){
		HrisApplication.setCurrentUser(null);
		HrisApplication.setPermissionLevel(0);
		shellCommands.clearConsole();
		CustomPromptProvider.changePrompt("disconnected");
		try {
			connect();
		}catch(Exception e){}
	}

	@ShellMethodAvailability("currentSessionIn")
	@ShellMethod(key={"c", "connect", "-c", "sign-in", "in"}, value="Connect to HRIS")
	public void connect() throws ParseException {
			shellCommands.clearConsole();
			if(HrisApplication.getCurrentUser() == null) {
				//Optional<Account> possibleUser;
				Account possibleUser;
				Map<String, String> options = new HashMap<>();
				options.put("A", "Sign In");
				options.put("B", "Register");
				String selection = inputReader.listInput(
						"Welcome to McMillian  and Associates HRIS",
						"Please select an option [] provided above.",
						options, // HashMap
						true);
				if(selection.equalsIgnoreCase("A")){
					shellResult.printSuccess("Sign In to Account");
					shellResult.printInfo("Input \"exit\" to cancel");
					String username = inputReader.prompt("Username");
					if(username.equalsIgnoreCase("exit")){
						connect();
						return;
					}
					String password = inputReader.prompt("Password", null, false);
					possibleUser = aService.authenticate( new Account(username, password));
					if(possibleUser != null){
						shellResult.printSuccess("Successfully Logged In!");
						HrisApplication.setCurrentUser(possibleUser);
						HrisApplication.setPermissionLevel(shellCommands.getPermissionLevel(possibleUser.getRoles()));
						HrisApplication.setStringRole(shellCommands.getPermissionString(possibleUser.getRoles()));


						CustomPromptProvider.changePrompt("connected");

						if(HrisApplication.getPermissionLevel() > 0 && (possibleUser.getEmployee().getFirstName() == null || possibleUser.getEmployee().getFirstName().equalsIgnoreCase(""))){
							shellCommands.clearConsole();
							shellResult.printInfo("No Employee Information! Please input now!");
							do{
								shellCommands.updateItem("Employee Information", "First Name", HrisApplication.getCurrentUser(), false);
								shellCommands.updateItem("Employee Information", "Last Name", HrisApplication.getCurrentUser(), false);
								shellCommands.updateItem("Employee Information", "Date Of Birth", HrisApplication.getCurrentUser(), false);
								break;
							}while(true);
						}

						shellCommands.clearConsole();
						shellCommands.displayBanner();
						shellResult.printList("Commands", shellCommands.getCommandGroup(HrisApplication.getPermissionLevel()));
						if(HrisApplication.getPermissionLevel() > 0){
							shellResult.printBright(String.format("Welcome %s %s", HrisApplication.getStringRole(), possibleUser.getEmployee().getFirstName()));
						}else{
							shellResult.printBright(String.format("Welcome %s %s!", "Candidate", possibleUser.getUsername()));
						}

					}else{
						shellResult.printError("Error, try again!");
						connect();
					}
				}else if(selection.equalsIgnoreCase("B")){
					shellResult.printSuccess("Register Account");
					Account user = shellCommands.createAccount("Candidate");
					if(user == null){
						connect();
					}else{
						shellResult.printSuccess("Successfully Registered Candidate Account!");
						HrisApplication.setCurrentUser(user);
						HrisApplication.setPermissionLevel(shellCommands.getPermissionLevel(user.getRoles()));
						HrisApplication.setStringRole("Candidate");
						CustomPromptProvider.changePrompt("connected");
						shellCommands.clearConsole();
						shellCommands.displayBanner();
						shellResult.printList("Commands", shellCommands.getCommandGroup(HrisApplication.getPermissionLevel()));
						shellResult.printBright(String.format("Welcome %s %s!", "Candidate", user.getUsername()));
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

	public Availability candidateRestricted(){

		Account authUser = aService.findAccountById(HrisApplication.getCurrentUser().getId());

		if(authUser == null){
			return Availability.unavailable("Not Connected!");
		}

		if(shellCommands.getPermissionLevel(authUser.getRoles()) == ERole.ROLE_CANDIDATE.getID()){
			return HrisApplication.getSession() ? Availability.available() : Availability.unavailable("Not Connected");
		}

		return Availability.unavailable("Invalid Permission Level");
	}

	@ShellMethod(key="view", value="View Company Information")
	@ShellMethodAvailability("candidateAvailability")
	public void candidateMethod(){
		shellCommands.clearConsole();
		shellCommands.displayBanner();
		try{
			//Paths.get("backend", "src", "main", "resources", "banner.txt"))
			String filepath = new File("").getAbsolutePath();
			BufferedReader read = new BufferedReader(new FileReader(filepath.concat("/src/main/resources/mcma.txt")));
			String line;
			while((line = read.readLine()) != null){
				shellResult.printBright(line);
			}
		}catch(IOException e){
			shellResult.printError(e.toString());
		}
		do {
			if (inputReader.finishedPrompt()) {
				shellCommands.clearConsole();
				shellCommands.displayBanner();
				shellResult.printList("Commands", shellCommands.getCommandGroup(HrisApplication.getPermissionLevel()));
				break;
			}
		}while(true);
	}

}

@ShellComponent
@ShellCommandGroup("Employee")
@Transactional
class EmployeeCommands {

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
	@Autowired
	EmployeeService eService;
	@Autowired
	PayrollService prService;
	@Autowired
	TableDisplay tDisplay;


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

	public Availability employeeRestricted(){
		Account authUser = aService.findAccountById(HrisApplication.getCurrentUser().getId());

		if(authUser == null){
			return Availability.unavailable("Not Connected!");
		}

		if(shellCommands.getPermissionLevel(authUser.getRoles()) == ERole.ROLE_EMPLOYEE.getID()){
			return HrisApplication.getSession() ? Availability.available() : Availability.unavailable("Not Connected");
		}

		return Availability.unavailable("Invalid Permission Level");
	}


	@ShellMethod(key="cIn", value="Clock in")
	@ShellMethodAvailability("employeeAvailability")
	public void clockIn(){
		Account currentUser = HrisApplication.getCurrentUser();
		Employee currentEmp = currentUser.getEmployee();

		if(currentEmp.getClockedIn() == null || !currentEmp.getClockedIn()){
			Timesheet timesheet = new Timesheet(currentEmp);
			timesheet.setStart(new Date());
			timesheet.setEmployee(eService.findEmployee(currentEmp.getId()));
			Timesheet t = tService.createTimesheet(timesheet);

			currentEmp.setClockedIn(true);
			eService.updateEmployee(currentEmp.getId(), currentEmp);

			shellCommands.clearConsole();
			shellCommands.displayBanner();
			shellResult.printList("Commands", shellCommands.getCommandGroup(HrisApplication.getPermissionLevel()));
			shellResult.printSuccess("Clocked in Successfully");

		}else{
			shellResult.printError("Current User is already clocked in!");
		}
	}

	@ShellMethod(key="cOut", value="Clock out")
	@ShellMethodAvailability("employeeAvailability")
	public void clockOut(){
		Account currentUser = HrisApplication.getCurrentUser();
		Employee currentEmp = currentUser.getEmployee();

		if(currentEmp.getClockedIn() != null && currentEmp.getClockedIn()){
			Timesheet timesheet = tRepo.findTopByEmployeeOrderByIdDesc(currentEmp);

			if(timesheet != null){
				timesheet.setEnd(new Date());
				currentEmp.setClockedIn(false);

				prService.calculatePayroll(currentEmp);
				eService.updateEmployee(currentEmp.getId(), currentEmp);

				shellCommands.clearConsole();
				shellCommands.displayBanner();
				shellResult.printList("Commands", shellCommands.getCommandGroup(HrisApplication.getPermissionLevel()));
				shellResult.printSuccess("Clocked out Successfully!");
			}else{
				shellResult.printError("Error finding latest Timesheet");
			}
		}else{
			shellResult.printError("Current User is not clocked in!");

		}
	}

	@ShellMethod(key="update", value="Update information")
	@ShellMethodAvailability("employeeAvailability")
	public void updateMe() throws ParseException {
		boolean active = true;
		Map<String, String> options = new HashMap<>();
		options.put("A", "Account Information");
		options.put("B", "Personal Information");
		options.put("X", "EXIT");

		boolean changed = false;

		do{
			shellCommands.clearConsole();
			String selection = inputReader.listInput("Update",
					"Select an option [] above to update",
					options,
					true);
			if(selection.equalsIgnoreCase("X")){
				break;
			}else{
				Map<String, String> items = new HashMap<>();
				if(selection.equalsIgnoreCase("A")){
					items.put("A", "Username");
					items.put("B", "Password");

				}else{
					items.put("A", "First Name");
					items.put("B", "Last Name");
					items.put("C", "Date Of Birth");
					//items.put("D", "Benefits");

				}
				items.put("X", "BACK");
				do{
					shellCommands.clearConsole();
					String selectedItem = inputReader.listInput(String.format("Update %s", options.get(selection)),
							"Select an option [] above to update",
							items,
							true);
					if(selectedItem.equals("X")){break;}
					try {
						shellCommands.updateItem(options.get(selection), items.get(selectedItem), HrisApplication.getCurrentUser());
						changed = true;
					}catch(Exception e){
						shellResult.printError(e.toString());
					}
				}while(active);
			}

		}while(active);
		shellCommands.clearConsole();
		shellCommands.displayBanner();
		shellResult.printList("Commands", shellCommands.getCommandGroup(HrisApplication.getPermissionLevel()));
		if (changed) {
			shellResult.printSuccess("Updated Changes Successfully!");
		}else{
			shellResult.printInfo("No Changes Detected.");
		}

	}

	@ShellMethod(key="info", value="View Employee Information")
	@ShellMethodAvailability("employeeAvailability")
	public void info(){
		shellCommands.clearConsole();
		tDisplay.employeeTable(HrisApplication.getCurrentUser().getEmployee());
		inputReader.finishedPrompt();
		shellCommands.clearConsole();
		shellCommands.displayBanner();
		shellResult.printList("Commands", shellCommands.getCommandGroup(HrisApplication.getPermissionLevel()));
	}

	@ShellMethod(key="payrolls", value="View Your Payrolls (Current Month)")
	@ShellMethodAvailability("employeeAvailability")
	public void payrolls(){
		shellCommands.clearConsole();
		tDisplay.listPayrolls(HrisApplication.getCurrentUser().getEmployee());
		inputReader.finishedPrompt();
		shellCommands.clearConsole();
		shellCommands.displayBanner();
		shellResult.printList("Commands", shellCommands.getCommandGroup(HrisApplication.getPermissionLevel()));
	}

	@ShellMethod(key="timesheet", value="View Your Timesheets (Last 3)")
	@ShellMethodAvailability("employeeAvailability")
	public void timesheet(){
		shellCommands.clearConsole();
		tDisplay.listTimeSheets(HrisApplication.getCurrentUser().getEmployee());
		inputReader.finishedPrompt();
		shellCommands.clearConsole();
		shellCommands.displayBanner();
		shellResult.printList("Commands", shellCommands.getCommandGroup(HrisApplication.getPermissionLevel()));
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
	@Autowired
	EmployeeService eService;
	@Autowired
	DepartmentEmployeeService dEService;
	@Autowired
	DepartmentService dService;

	public Availability managerAvailability(){
		Account authUser = aService.findAccountById(HrisApplication.getCurrentUser().getId());

		if(authUser == null){
			return Availability.unavailable("Not Connected!");
		}

		if(shellCommands.getPermissionLevel(authUser.getRoles()) >= ERole.ROLE_MANAGER.getID()){
			return HrisApplication.getSession() ? Availability.available() : Availability.unavailable("Not Connected");
		}

		return Availability.unavailable("Invalid Permission Level");
	}

	public Availability managerRestricted(){
		Account authUser = aService.findAccountById(HrisApplication.getCurrentUser().getId());

		if(authUser == null){
			return Availability.unavailable("Not Connected!");
		}

		if(shellCommands.getPermissionLevel(authUser.getRoles()) == ERole.ROLE_MANAGER.getID()){
			return HrisApplication.getSession() ? Availability.available() : Availability.unavailable("Not Connected");
		}

		return Availability.unavailable("Invalid Permission Level");
	}

	// DEPRECATED : Manager Promote Candidate
	/*@ShellMethod(key="promote", value="Promote Candidate Account to Employee")
	@ShellMethodAvailability("managerAvailability")
	public void promoCandidate(){
		shellResult.printInfo("Promote Candidate Account");
		String type = null;
		String target = null;
		Map<String, String> options = new HashMap<>();
		options.put("A", "Username");
		options.put("B", "Account ID");
		do{
			type = options.get(inputReader.listInput("Find by",
					"Please select an option [] provided above.",
					options,
					true));
		}while(type == null);

		do{
			target = inputReader.prompt(String.format("Input %s", type));
		}while(target == null);

		if(shellCommands.promoteAccount(type, target)){
			shellResult.printSuccess("Account Promoted Successfully!");
		}else{
			shellResult.printError("Failed to Promote Account!");
		}
	}*/

	@ShellMethod(key="performance", value="Add performance review to Employee")
	@ShellMethodAvailability("managerRestricted")
	public void performance(){
		if(shellCommands.makePerformance(HrisApplication.getCurrentUser())){
			shellCommands.clearConsole();
			shellCommands.displayBanner();
			shellResult.printList("Commands", shellCommands.getCommandGroup(HrisApplication.getPermissionLevel()));
			shellResult.printInfo("Performance Review Successful!");
		}else{
			shellCommands.clearConsole();
			shellCommands.displayBanner();
			shellResult.printList("Commands", shellCommands.getCommandGroup(HrisApplication.getPermissionLevel()));
			shellResult.printInfo("Performance Review Scrapped.");
		}
	}

	@ShellMethod(key="view-performance", value="View performance review of Employee")
	@ShellMethodAvailability("managerAvailability")
	public void viewPerformance(){
		shellCommands.clearConsole();
		Map<String, String> options = new HashMap<>();
		options.put("A", "Account ID");
		options.put("B", "Account Username");
		options.put("C", "Employee ID");
		options.put("X", "CANCEL");

		String selection = null;
		Account target = null;

		do{
			selection = inputReader.listInput("Employee",
					"Please select an option [] above.",
					options,
					true);

			if(selection.equalsIgnoreCase("X")){
				shellCommands.clearConsole();
				shellCommands.clearConsole();
				shellCommands.displayBanner();
				shellResult.printList("Commands", shellCommands.getCommandGroup(HrisApplication.getPermissionLevel()));
				return;
			}

			String input = inputReader.prompt(options.get(selection) + "(input \"exit\" to cancel)");

			if(input.equalsIgnoreCase("exit")){
				shellCommands.clearConsole();
				continue;
			}

			if(selection.equalsIgnoreCase("A")){
				Long id;
				try{
					id = Long.parseLong(input);
				}catch(NumberFormatException e){
					shellResult.printError("Invalid Input");
					continue;
				}

				if(aService.existsById(id)){
					target = aService.findAccountById(id);
				}else{
					shellCommands.clearConsole();
					shellResult.printError("Account Does Not Exist!");
					continue;
				}
			}else if(selection.equalsIgnoreCase("B")){
				if(inputReader.confirmationPrompt(input)){
					Optional<Account> opt = aService.findByUsername(input);
					if(opt.isPresent()){
						target = opt.get();
					}else{
						shellCommands.clearConsole();
						shellResult.printError("Account Does Not Exist!");
						continue;
					}
				}else{
					shellCommands.clearConsole();
					continue;
				}
			}else if(selection.equalsIgnoreCase("C")){
				Long id;
				try{
					id = Long.parseLong(input);
				}catch(NumberFormatException e){
					shellResult.printError("Invalid Input");
					continue;
				}

				if(eService.existsById(id)){
					target = eService.findEmployee(id).getAccount();
				}else{
					shellCommands.clearConsole();
					shellResult.printError("Account Does Not Exist!");
					continue;
				}
			}

			if(inputReader.confirmationPrompt(String.format("Employee %s %s", target.getEmployee().getFirstName(), target.getEmployee().getLastName()))){
				if(HrisApplication.getPermissionLevel() == ERole.ROLE_MANAGER.getID()){
					if(!dEService.existsByEmployeeAndDepartment(target.getEmployee(), dService.findByManagerId(HrisApplication.getCurrentUser().getEmployee()))){
						shellCommands.clearConsole();
						shellResult.printError("Employee Not In Department!");
						continue;
					}
				}
				if(shellCommands.getPermissionLevel(target.getRoles()) > ERole.ROLE_MANAGER.getID() || shellCommands.getPermissionLevel(target.getRoles()) > HrisApplication.getPermissionLevel()){
					shellCommands.clearConsole();
					shellResult.printError("Invalid Permission Level");
					continue;
				}
				break;
			}

		}while(true);


		shellCommands.viewPerfomance(target);
		shellCommands.clearConsole();
		shellCommands.displayBanner();
		shellResult.printList("Commands", shellCommands.getCommandGroup(HrisApplication.getPermissionLevel()));
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

	@Autowired
	EmployeeService eService;

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

	public Availability hrRestricted(){
		Account authUser = aService.findAccountById(HrisApplication.getCurrentUser().getId());

		if(authUser == null){
			return Availability.unavailable("Not Connected!");
		}

		if(shellCommands.getPermissionLevel(authUser.getRoles()) == ERole.ROLE_HR.getID()){
			return HrisApplication.getSession() ? Availability.available() : Availability.unavailable("Not Connected");
		}

		return Availability.unavailable("Invalid Permission Level");
	}

	@ShellMethod("Deactivate CLI")
	@ShellMethodAvailability("hrAvailability")
	public void deactivate(){
		HrisApplication.deactivate();
	}

	@ShellMethod(key="promote", value="Advanced Promotion")
	@ShellMethodAvailability("hrAvailability")
	public void hPromote(){
		shellCommands.clearConsole();
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
			type = options.get(inputReader.listInput("Find by",
					"Please select an option [] provided above.",
					options,
					true));
		}while(type == null);

		do{
			target = inputReader.prompt(String.format("Input %s", type));
		}while(target == null);

		shellCommands.clearConsole();

		do{
			role = roles.get(inputReader.listInput("New Role",
					"Please select an option [] provided above.",
					roles,
					true));
		}while(role == null);

		if(shellCommands.promoteAccount(type, target, role)){
			shellCommands.clearConsole();
			shellCommands.displayBanner();
			shellResult.printList("Commands", shellCommands.getCommandGroup(HrisApplication.getPermissionLevel()));
			shellResult.printSuccess("Account Promoted Successfully!");
		}else{
			shellCommands.clearConsole();
			shellCommands.displayBanner();
			shellResult.printList("Commands", shellCommands.getCommandGroup(HrisApplication.getPermissionLevel()));
			shellResult.printError("Failed to Promote Account!" + " " + shellCommands.getDebug());
		}
	}

	@ShellMethod(key="edit", value="Edit Employee")
	@ShellMethodAvailability("hrAvailability")
	public void editEmployee(){
		shellCommands.clearConsole();
		shellResult.printInfo("Edit Employee:");
		Account target = null;
		Map<String, String> typeOptions = new HashMap<>();
		typeOptions.put("A", "Account ID");
		typeOptions.put("B", "Account Username");
		typeOptions.put("C", "Employee ID");
		typeOptions.put("D", "Self");
		typeOptions.put("X", "Cancel");

		boolean changed = false;

		String type = inputReader.listInput("Find by: ",
				"Please selection an option [] above",
				typeOptions,
				true);

		do{
			if(type.equalsIgnoreCase("X")){
				shellCommands.clearConsole();
				shellCommands.displayBanner();
				shellResult.printList("Commands", shellCommands.getCommandGroup(HrisApplication.getPermissionLevel()));
				return;

			}
			String t = " ";
			if(!type.equalsIgnoreCase("D")) {
				t = inputReader.prompt(typeOptions.get(type) + "(enter \"exit\" to cancel)");
			}

			if(t.equalsIgnoreCase("exit")){
				shellCommands.clearConsole();
				shellCommands.displayBanner();
				shellResult.printList("Commands", shellCommands.getCommandGroup(HrisApplication.getPermissionLevel()));
				editEmployee();
				return;
			}

			Account holder = null;

			if(type.equals("A")){
				holder = aService.findAccountById(Long.parseLong(t));
				if(shellCommands.getPermissionLevel(holder.getRoles()) >= HrisApplication.getPermissionLevel()){
					shellResult.printWarning("Unable to edit Employee! Permission Level Not High Enough!");
					holder = null;
				}
			}else if(type.equals("B")){
				holder = aService.findByUsername(t).get();
				if(shellCommands.getPermissionLevel(holder.getRoles()) >= HrisApplication.getPermissionLevel()){
					shellResult.printWarning("Unable to edit Employee! Permission Level Not High Enough!");
					holder = null;
				}
			}else if(type.equals("C")){
				holder = eService.findEmployee(Long.parseLong(t)).getAccount();
				if(holder == null || shellCommands.getPermissionLevel(holder.getRoles()) >= HrisApplication.getPermissionLevel()){
					shellResult.printWarning("Unable to edit Employee! Invalid Target!");
					holder = null;
				}
			}else if(type.equals("D")){
				holder = HrisApplication.getCurrentUser();
			}

			target = holder;
		}while(target == null);

		boolean active = true;
		Map<String, String> options = new HashMap<>();
		options.put("A", "Employee Information");
		options.put("X", "EXIT");

		do{
			shellCommands.clearConsole();
			String selection = inputReader.listInput("Edit Employee",
					"Select an option [] above to update",
					options,
					true);
			if(selection.equalsIgnoreCase("X")){

				break;
			}else{
				Map<String, String> items = new HashMap<>();
				items.put("A", "Payrate"); // Done
				items.put("B", "Department"); // Done
				items.put("C", "Training"); // Done
				items.put("D", "Benefits"); // Done

				if(type.equalsIgnoreCase("D")){
					items.put("F", "First Name");
					items.put("G", "Last Name");
					items.put("H", "Date of Birth");

				}

				items.put("X", "BACK");
				do{
					shellCommands.clearConsole();
					String selectedItem = inputReader.listInput(String.format("Update %s", options.get(selection)),
							"Select an option [] above to update",
							items,
							true);
					if(selectedItem.equals("X")){break;}
					try {
						shellCommands.updateItem(options.get(selection), items.get(selectedItem), target, true);
						changed = true;
					}catch(Exception e){
						shellResult.printError(e.toString());
					}
				}while(active);
			}

		}while(active);
		shellCommands.clearConsole();
		shellCommands.displayBanner();
		shellResult.printList("Commands", shellCommands.getCommandGroup(HrisApplication.getPermissionLevel()));
		if (changed) {
			shellResult.printSuccess("Updated Changes Successfully!");
		}else{
			shellResult.printInfo("No Changes Detected.");
		}
	}

	@ShellMethod(key="manage", value="Manage Company")
	@ShellMethodAvailability("hrAvailability")
	public void manageCompany(){
		Boolean changed = false;
		Map<String, String> manageable = new HashMap<>();
		manageable.put("A", "Position");
		manageable.put("B", "Benefit");
		manageable.put("C", "Department");
		manageable.put("D", "Training");
		manageable.put("E", "Employee"); // Create Employee | Delete Employee
		manageable.put("X", "EXIT");

		do {
			shellCommands.clearConsole();
			shellResult.printInfo("Manage:");
			String manageSelection = inputReader.listInput("Manage",
					"Please select an option [] above to manage.",
					manageable,
					true);
			if(manageSelection.equalsIgnoreCase("X")){
				break;
			}
			if(shellCommands.manageCompany(manageable.get(manageSelection))){
				changed = true;
			}
		}while(true);

		shellCommands.clearConsole();
		shellCommands.displayBanner();
		shellResult.printList("Commands", shellCommands.getCommandGroup(HrisApplication.getPermissionLevel()));
		if(changed){
			shellResult.printSuccess("Changes Updated Successfully!");
		}else{
			shellResult.printInfo("No Changes Detected.");
		}
	}
}