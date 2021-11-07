package com.wozu.hris.cli_resources;

import org.jline.reader.LineReader;
import org.springframework.util.StringUtils;

import java.util.*;

public class InputReader {

    ShellResult shellResult;

    public static final Character default_mask = '*';

    private Character mask;
    private LineReader lineReader;

    public InputReader(LineReader lineReader, ShellResult shellResult){
        this.lineReader = lineReader;
        this.shellResult = shellResult;
        this.mask = default_mask;
    }

    public InputReader(LineReader lineReader, ShellResult shellResult, Character mask){
        this.lineReader = lineReader;
        this.shellResult = shellResult;
        this.mask = mask != null ? mask : default_mask;
    }

    public String prompt(String prompt){
        return prompt(prompt, null, true);
    }

    public String prompt(String prompt, String default_value, boolean display){
        String answer;

        if(display){
            answer = lineReader.readLine(prompt+": ");
        }else{
            answer = lineReader.readLine(prompt+": ", mask);
        }
        if(!StringUtils.hasText(answer)){
            return default_value;
        }
        return answer;
    }

    public String listInput(String inputType, String prompt, Map<String, String> options, boolean ignoreCase){
        String answer;
        Set<String> possibleAnswers = new HashSet<>(options.keySet());

        shellResult.print(String.format("%s: ", inputType));
        do {
            for(Map.Entry<String, String> option: options.entrySet()){
                String defaultMarker = null;
                if(defaultMarker != null){
                    shellResult.printInfo(String.format("%s [%s] %s", defaultMarker, option.getKey(), option.getValue()));
                }else{
                    shellResult.print(String.format("[%s] %s", option.getKey(), option.getValue()));
                }
            }

            answer = lineReader.readLine(String.format("%s: ", prompt));
        } while (!containsString(possibleAnswers, answer, ignoreCase) && "" != answer);
        return answer;
    }

    private boolean containsString(Set<String> l, String s, boolean ignoreCase){
        if(!ignoreCase){
            return l.contains(s);
        }
        Iterator<String> i = l.iterator();
        while(i.hasNext()){
            if(i.next().equalsIgnoreCase(s)){
                return true;
            }
        }
        return false;
    }
}