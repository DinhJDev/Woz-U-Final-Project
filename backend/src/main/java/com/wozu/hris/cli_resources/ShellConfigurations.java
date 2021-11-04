package com.wozu.hris.cli_resources;

import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class ShellConfigurations {

    @Bean
    public ShellResult shellResult(@Lazy Terminal terminal){
        return new ShellResult(terminal);
    }

    @Bean
    public InputReader inputReader(@Lazy LineReader lineReader, ShellResult shellResult){return new InputReader(lineReader, shellResult);}
}
