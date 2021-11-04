package com.wozu.hris.cli_resources;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomPromptProvider implements PromptProvider {
    private static AttributedString prompt = new AttributedString("CONNECT:>",
            AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));

    @Override
    public AttributedString getPrompt() {
        return prompt;
    }

    public static void changePrompt(String promptState){
        if(promptState != null){
            if(promptState.equalsIgnoreCase("connected")){
                prompt = new AttributedString("McMR-HRIS:>",
                        AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE));
            }else{
                prompt = new AttributedString("CONNECT:>",
                        AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
            }
        }
    }
}
