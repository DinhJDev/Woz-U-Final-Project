package com.wozu.hris.cli_resources;


import lombok.SneakyThrows;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultBannerProvider;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

// spring.banner.location=banner.txt

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomBannerProvider extends DefaultBannerProvider {

    @SneakyThrows
    public String getBanner(){
        StringBuffer buf = new StringBuffer();
        String filepath = new File("").getAbsolutePath();
        BufferedReader read = new BufferedReader(new FileReader(filepath.concat("/src/main/resources/banner.txt")));
        String line;
        while((line = read.readLine()) != null){
            buf.append(line);
        }
        return buf.toString();
    }


}
