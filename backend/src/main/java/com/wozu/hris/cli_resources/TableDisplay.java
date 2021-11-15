package com.wozu.hris.cli_resources;

import antlr.ASTNULLType;
import com.wozu.hris.cli_resources.ShellResult;
import com.wozu.hris.models.*;
import com.wozu.hris.repositories.*;
import com.wozu.hris.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TableDisplay {

    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");

    @Autowired
    private ShellResult shellResult;
    TrainingRepository trainingRepository;
    DepartmentRepository deptRepo;
    PayrollRepository payrollRepo;
    BenefitRepository beneRepo;
    PositionRepository posRepo;

    @Autowired
    TrainingService tService;
    @Autowired
    DepartmentService dService;
    @Autowired
    PayrollService pRService;
    @Autowired
    BenefitService bService;
    @Autowired
    PositionService posService;


    @Autowired
    private ShellCommands shellCommands;

    // Display Personal Information
    public void employeeTable(Employee employee) {
        //this should only take in one line... hm

        String[] temp = new String[12];
        temp[0] = employee.getId().toString(); //
        temp[1] = employee.getFirstName(); //
        temp[2] = employee.getLastName(); //
        temp[3] = format.format(employee.getDateOfBirth()); //
        temp[4] = employee.getEmployeeDepartmentString();
        temp[5] = shellCommands.getPermissionString(employee.getAccount().getRoles());
        temp[6] = employee.getPayrate() != null ? shellCommands.getPermissionLevel(employee.getAccount().getRoles()) >= ERole.ROLE_MANAGER.getID() ? employee.getPayrate().getSalary().toString() + "/year" : employee.getPayrate().getHourlyRate().toString() + "/hour" : "N/A";
        temp[7] = employee.totalHours();
        temp[8] = employee.getEmployeeTrainingString();
        temp[9] = employee.getlastPerformance();
        temp[10] = employee.getBenefitName();
        temp[11] = employee.getStatus();

        Object[][] set = new String[][] {
                {"ID", "First Name", "Last Name", "Date of Birth", "Department", "Position", "Payrate", "Total Hours", "Training", "Performance", "Benefits", "Status"},
                temp
        };

        TableModel model = new ArrayTableModel(set);
        TableBuilder tableBuilder = new TableBuilder(model);


        tableBuilder.addFullBorder(BorderStyle.oldschool);
        shellResult.print(tableBuilder.build().render(80));

    }

    // Display Team
    public void managerTable(Employee[] employee) {

        String[] temp = new String[8];
        Object[][] set = new String[employee.length + 1][8];
        set[0] = new String[]{"ID", "First name", "Last name", "Department", "Position", "Training", "Performance", "Status"};
        int count = 1;
        for (Employee e : employee){
            temp[0] = e.getId().toString();
            temp[1] = e.getFirstName();
            temp[2] = e.getLastName();
            temp[3] = e.getEmployeeDepartmentString();
            temp[4] = e.getPosition();
            temp[5] = e.getEmployeeTrainingString();
            temp[6] = e.getlastPerformance();
            temp[7] = e.getStatus();

            set[count] = temp;
            count ++;
        }

        TableModel model = new ArrayTableModel(set);
        TableBuilder tableBuilder = new TableBuilder(model);


        tableBuilder.addFullBorder(BorderStyle.oldschool);
        shellResult.print(tableBuilder.build().render(80));

    }

    // Display All Personnel
    public void hrTable(List<Employee> employee) {
        Object[][] set = new String[employee.size() + 1][13];
        set[0] = new String[] {"ID", "First Name", "Last Name", "Date of Birth", "Department", "Position", "Payrate", "Total Hours", "Training", "Performance", "Benefits", "Role", "Status"};
        int count = 1;
        for (Employee e : employee){
            String[] temp = new String[13];
            temp[0] = e.getId().toString();
            temp[1] = e.getFirstName();
            temp[2] = e.getLastName();
            temp[3] = format.format(e.getDateOfBirth());
            temp[4] = e.getEmployeeDepartmentString();
            temp[5] = shellCommands.getPermissionString(e.getAccount().getRoles());
            temp[6] = e.getPayrate() != null ? shellCommands.getPermissionLevel(e.getAccount().getRoles()) >= ERole.ROLE_MANAGER.getID() ? e.getPayrate().getSalary().toString() + "/year" : e.getPayrate().getHourlyRate().toString() + "/hour" : "N/A";
            temp[7] = e.totalHours();
            temp[8] = e.getEmployeeTrainingString();
            temp[9] = e.getlastPerformance();
            temp[10] = e.getBenefitName();
            temp[11] = e.getPosition();
            temp[12] = e.getStatus();

            set[count] = temp;
            count++;
        }
        TableModel model = new ArrayTableModel(set);
        TableBuilder tableBuilder = new TableBuilder(model);


        tableBuilder.addFullBorder(BorderStyle.oldschool);
        shellResult.print(tableBuilder.build().render(80));

    }

    // Candidate Display
    public void candidateTable(Employee employee) {

        String[] temp = new String[3];
        temp[0] = employee.getFirstName();
        temp[1] = employee.getLastName();
        temp[2] = "Pending"; //find status

        Object[][] set = new String[][] {
                {"First name", "Last name", "Status"},
                temp
        };

        TableModel model = new ArrayTableModel(set);
        TableBuilder tableBuilder = new TableBuilder(model);


        tableBuilder.addFullBorder(BorderStyle.oldschool);
        shellResult.print(tableBuilder.build().render(80));

    }

    public void listDepartments(){
        List<Department> deptList = dService.allDepts();
        Object[][] set = new String[deptList.size()][1];
        int count = 0;
        for (Department d : deptList) {
            String[] temp = new String[2];
            temp[0] = String.valueOf(d.getId());
            temp[1] = d.getName();
            set[count] = temp;
            count ++;
        }

        TableModel model = new ArrayTableModel(set);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addFullBorder(BorderStyle.oldschool);
        shellResult.print(tableBuilder.build().render(80));
    }

    public void listPayrolls(Employee e){
        Date start = shellCommands.getStartOfMonth();
        List<Payroll> payrollList = pRService.findAllByDateBetweenAndEmployeeOrderByIdAsc(start, new Date(), e);
        Object[][] set = new String[payrollList.size()][1];
        int count = 0;
        for (Payroll p : payrollList) {
            String[] temp = new String[2];
            temp[0] = String.valueOf(p.getId());
            temp[1] = String.format("$%.2f", p.getAmount());
            set[count] = temp;
            count ++;
        }

        TableModel model = new ArrayTableModel(set);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addFullBorder(BorderStyle.oldschool);
        shellResult.print(tableBuilder.build().render(80));

    }

    public void listTrainings(){
        List<Training> trainingList = tService.allTrainings();
        Object[][] set = new String[trainingList.size()][1];
        int count = 0;
        for (Training t : trainingList) {
            String[] temp = new String[2];
            temp[0] = String.valueOf(t.getId());
            temp[1] = t.getTrainingName();
            set[count] = temp;
            count ++;
        }

        TableModel model = new ArrayTableModel(set);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addFullBorder(BorderStyle.oldschool);
        shellResult.print(tableBuilder.build().render(80));
    }

    public void listBenefits(){
        List<Benefit> benefitList = bService.allBenefits();
        Object[][] set = new String[benefitList.size()][1];
        int count = 0;
        for (Benefit b : benefitList) {
            String[] temp = new String[2];
            temp[0] = String.valueOf(b.getId());
            temp[1] = b.getName();
            set[count] = temp;
            count ++;
        }

        TableModel model = new ArrayTableModel(set);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addFullBorder(BorderStyle.oldschool);
        shellResult.print(tableBuilder.build().render(80));
    }

    public void listPositions(){
        List<Position> positionList = posService.allPos();
        Object[][] set = new String[positionList.size()][1];

        int count = 0;
        for (Position p : positionList) {
            String[] temp = new String[2];
            temp[0] = String.valueOf(p.getId());
            temp[1] = p.getName();
            set[count] = temp;
            count++;
        }

        TableModel model = new ArrayTableModel(set);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addFullBorder(BorderStyle.oldschool);
        shellResult.print(tableBuilder.build().render(80));
    }

    public void listPerformance(Employee e){
        List<Performance> perfLisr = e.getReviews();
        Object[][] set = new String[perfLisr.size()+1][3];
        int count = 0;
        set[0] = new String[] {"Date", "Performance", "Manager"};
        for (Performance p : perfLisr) {
            String[] temp = new String[4];
            temp[0] = String.valueOf(p.getId());
            temp[1] = p.getCreatedAt().toString();
            temp[2] = p.getComment();
            temp[3] = p.getReviewer().getFirstName() + " " + p.getReviewer().getLastName();
            set[count] = temp;
            count ++;
        }
        TableModel model = new ArrayTableModel(set);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addFullBorder(BorderStyle.oldschool);
        shellResult.print(tableBuilder.build().render(80));
    }

    public void listTimeSheets(Employee e){
        List<Timesheet> timeList = e.getTimesheets();
        timeList = timeList.size() > 3 ? timeList.subList(timeList.size()-3, timeList.size()) : timeList;
        Object[][] set = new String[4][3];
        int count = 1;
        set[0] = new String[] {"Clock-In", "Clock-Out", "Hours"};
        for (Timesheet t : timeList){
            String[] temp = new String[3];
            temp[0] = String.valueOf(t.getId());
            temp[1] = timeFormat.format(t.getStart());
            temp[2] = t.getEnd() != null ? timeFormat.format(t.getEnd()) : "N/A";

            set[count] = temp;
            count ++;
        }
        TableModel model = new ArrayTableModel(set);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addFullBorder(BorderStyle.oldschool);
        shellResult.print(tableBuilder.build().render(80));
    }


}
