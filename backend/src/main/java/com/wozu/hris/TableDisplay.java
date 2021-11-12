package com.wozu.hris;

import com.wozu.hris.cli_resources.ShellResult;
import com.wozu.hris.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;


@ShellComponent
public class TableDisplay {


    @Autowired
    ShellResult shellResult;

    @ShellMethod("Display personal Information")
    public void employeeTable(Employee employee) {
        //this should only take in one line... hm
        String[] temp = new String[12];
        temp[0] = employee.getId().toString();
        temp[1] = employee.getFirstName();
        temp[2] = employee.getLastName();
        temp[3] = employee.getDateOfBirth().toString();
        temp[4] = employee.getEmployeeDepartmentString();
        temp[5] = employee.getPosition();
        temp[6] = employee.getPayrate().toString();
        temp[7] = employee.totalHours();
        temp[8] = employee.getEmployeeTrainingString();
        temp[9] = employee.getPerformances().toString();
        temp[10] = employee.getBenefitName();
        temp[11] = employee.getStatus();

        Object[][] set = new String[][] {
                {"ID", "First Name", "Last Name", "Date of Birth", "Department", "Position", "Payrate", "Total Hours", "Training", "Performance", "Benefits", "Status"},
                temp
        };

        TableModel model = new ArrayTableModel(set);
        TableBuilder tableBuilder = new TableBuilder(model);


        shellResult.printInfo("oldschool border style");
        tableBuilder.addFullBorder(BorderStyle.oldschool);
        shellResult.print(tableBuilder.build().render(80));

    }

    @ShellMethod("Display team")
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


        shellResult.printInfo("oldschool border style");
        tableBuilder.addFullBorder(BorderStyle.oldschool);
        shellResult.print(tableBuilder.build().render(80));

    }

    @ShellMethod("Display all personnel")
    public void hrTable(Employee[] employee) {
        String[] temp = new String[13];
        Object[][] set = new String[employee.length + 1][13];
        set[0] = new String[] {"ID", "First Name", "Last Name", "Date of Birth", "Department", "Position", "Payrate", "Total Hours", "Training", "Performance", "Benefits", "Role", "Status"};
        int count = 0;
        for (Employee e : employee){
            temp[0] = e.getId().toString();
            temp[1] = e.getFirstName();
            temp[2] = e.getLastName();
            temp[3] = e.getDateOfBirth().toString();
            temp[4] = e.getEmployeeDepartmentString();
            temp[5] = e.getPosition();
            temp[6] = e.getPayrate().toString();
            temp[7] = e.totalHours();
            temp[8] = e.getEmployeeTrainingString();
            temp[9] = e.getlastPerformance();
            temp[10] = e.getBenefitName();
            temp[11] = e.getPosition();
            temp[12] = e.getStatus();

            set[count] = temp;
            count ++;
        }
        TableModel model = new ArrayTableModel(set);
        TableBuilder tableBuilder = new TableBuilder(model);


        shellResult.printInfo("oldschool border style");
        tableBuilder.addFullBorder(BorderStyle.oldschool);
        shellResult.print(tableBuilder.build().render(80));

    }

    @ShellMethod("Display candidate table")
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


        shellResult.printInfo("oldschool border style");
        tableBuilder.addFullBorder(BorderStyle.oldschool);
        shellResult.print(tableBuilder.build().render(80));

    }

}
