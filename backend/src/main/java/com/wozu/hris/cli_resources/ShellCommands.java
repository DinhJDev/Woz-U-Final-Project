package com.wozu.hris.cli_resources;

import com.wozu.hris.HrisApplication;
import org.springframework.boot.Banner;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
        try{
            //Paths.get("backend", "src", "main", "resources", "banner.txt"))
            String filepath = new File("").getAbsolutePath();
            BufferedReader read = new BufferedReader(new FileReader(filepath.concat("/src/main/resources/banner.txt")));
            String line;
            while((line = read.readLine()) != null){
                System.out.println(line);
            }
        }catch(IOException e){
            System.out.println(e);
        }
    }

}