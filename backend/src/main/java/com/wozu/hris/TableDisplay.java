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
        temp[4] = employee.getDepartment().toString();
        temp[5] = "N/A"; //find position...
        temp[6] = employee.getPayrate().toString();
        temp[7] = "N/A"; //find total hours
        temp[8] = employee.getEmployeeTrainings().toString();
        temp[9] = employee.getPerformances().toString();
        temp[10] = employee.getBenefit().toString();
        temp[11] = employee.getClockedIn().toString();

        Object[][] set = new String[][] {
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
        Object[][] set = new String[employee.length][8];
        int count = 0;
        for (Employee e : employee){
            temp[0] = e.getId().toString();
            temp[1] = e.getFirstName();
            temp[2] = e.getLastName();
            temp[3] = e.getDepartment().toString();
            temp[4] = "N/A"; //find position...
            temp[5] = e.getEmployeeTrainings().toString();
            temp[6] = e.getPerformances().toString();
            temp[7] = e.getClockedIn().toString();

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
        Object[][] set = new String[employee.length][13];
        int count = 0;
        for (Employee e : employee){
            temp[0] = e.getId().toString();
            temp[1] = e.getFirstName();
            temp[2] = e.getLastName();
            temp[3] = e.getDateOfBirth().toString();
            temp[4] = e.getDepartment().toString();
            temp[5] = "N/A"; //find position...
            temp[6] = e.getPayrate().toString();
            temp[7] = "N/A"; //find total hours
            temp[8] = e.getEmployeeTrainings().toString();
            temp[9] = e.getPerformances().toString();
            temp[10] = e.getBenefit().toString();
            temp[11] = "N/A"; //find role
            temp[12] = e.getClockedIn().toString();

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
        temp[0] = employee.getId().toString();
        temp[1] = employee.getFirstName();
        temp[2] = "N/A"; //find status

        Object[][] set = new String[][] {
                temp
        };

        TableModel model = new ArrayTableModel(set);
        TableBuilder tableBuilder = new TableBuilder(model);


        shellResult.printInfo("oldschool border style");
        tableBuilder.addFullBorder(BorderStyle.oldschool);
        shellResult.print(tableBuilder.build().render(80));

    }

}
