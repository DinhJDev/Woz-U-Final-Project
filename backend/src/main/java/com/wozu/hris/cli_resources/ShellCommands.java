package com.wozu.hris.cli_resources;

import com.wozu.hris.HrisApplication;
import org.springframework.boot.Banner;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class ShellCommands {

    public static ConfigurableApplicationContext context;

    public static void clearConsole(){
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }

    public static void displayBanner(){
        if(context != null){
            System.out.println("Success");
            Banner banner = context.getBean(Banner.class);
            banner.printBanner(context.getEnvironment(), HrisApplication.class, System.out);
        }
    }

    public static void setVar(ConfigurableApplicationContext c){
        context = context;
    }
}