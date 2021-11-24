package com.wozu.hris.cli_resources;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Value;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShellResult {
    @Value("${shell.out.info}")
    public String infoColor;

    @Value("${shell.out.success}")
    public String successColor;

    @Value("${shell.out.warning}")
    public String warningColor;

    @Value("${shell.out.error}")
    public String errorColor;

    @Value("${shell.out.bright}")
    public String brightColor;

    @Value("${shell.out.table.header}")
    public String tableHeader;

    @Value("${shell.out.table.border}")
    public String tableBorder;

    @Value("${shell.out.table.data}")
    public String tableData;

    @Value("${shell.out.table.prompt}")
    public String tablePrompt;

    private Terminal terminal;

    public ShellResult(Terminal terminal) {
        this.terminal = terminal;
    }

    public String getColored(String message, PromptColor color) {
        return (new AttributedStringBuilder()).append(message, AttributedStyle.DEFAULT.foreground(color.toJlineAttributedStyle())).toAnsi();
    }

    public String getInfoMessage(String message) {
        return getColored(message, PromptColor.valueOf(infoColor));
    }

    public String getSuccessMessage(String message) {
        return getColored(message, PromptColor.valueOf(successColor));
    }

    public String getWarningMessage(String message) {
        return getColored(message, PromptColor.valueOf(warningColor));
    }

    public String getErrorMessage(String message) {
        return getColored(message, PromptColor.valueOf(errorColor));
    }

    public String getTableHeader(String message){return getColored(message, PromptColor.valueOf(tableHeader));}

    public String getTableBorder(String message){return getColored(message, PromptColor.valueOf(tableBorder));}

    public String getTableData(String message){return getColored(message, PromptColor.valueOf(tableData));}

    public String getTablePrompt(String message){return getColored(message, PromptColor.valueOf(tablePrompt));}

    public void print(String message){
        print(message, null);
    }

    public void printSuccess(String message){
        print(message, PromptColor.valueOf(successColor));
    }

    public void printInfo(String message){
        print(message, PromptColor.valueOf(infoColor));
    }

    public void printWarning(String message){
        print(message, PromptColor.valueOf(warningColor));
    }

    public void printError(String message){
        print(message, PromptColor.valueOf(errorColor));
    }

    public void printBright(String message){print(message, PromptColor.valueOf(brightColor));}

    public void printTableHeader(String message){print(message, PromptColor.valueOf(tableHeader));}

    public void printTableBorder(String message){print(message, PromptColor.valueOf(tableBorder));}

    public void printTableData(String message){print(message, PromptColor.valueOf(tableData));}

    public void printTablePrompt(String message){print(message, PromptColor.valueOf(tablePrompt));}

    public void print(String message, PromptColor color){
        String toPrint = message;
        if(color != null){
            toPrint = getColored(message, color);
        }
        terminal.writer().println(toPrint);
        terminal.flush();
    }

    public void printSingle(String message, PromptColor color){
        String toPrint = message;
        if(color != null){
            toPrint = getColored(message, color);
        }
        terminal.writer().print(toPrint);
        terminal.flush();
    }

    public void printList(String header, LinkedHashMap<String, Map<String, String>> Olist){
        LinkedHashMap<String, Map<String, String>> l = Olist;
        if(l != null){
            System.out.println();
            printInfo(header);
            System.out.println();
            Iterator<Map.Entry<String, Map<String, String>>> parent = l.entrySet().iterator();
            while(parent.hasNext()){
                Map.Entry<String, Map<String, String>> list = parent.next();
                printInfo(String.format("----------[  %s  ]----------", list.getKey()));
                System.out.println();
                //printInfo("-------------------------------------");

                Iterator<Map.Entry<String, String>> child = (list.getValue()).entrySet().iterator();
                while(child.hasNext()){
                    Map.Entry command = child.next();
                    printInfo(String.format("   [%s] : %s", command.getKey(), command.getValue()));

                    child.remove();
                }
                System.out.println();
            }
        }else{
            printError("Error fetching Role Commands!");
        }
    }

    public void printTable(String header, LinkedHashMap<String, Map<String, String>> list){}
}