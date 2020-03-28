package com.jyw.lq.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {

    public static void main(String[] args) {
        String content = "abbbcbc";

        String pattern = "ab{1,3}?bc";
        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(content);
        if(matcher.find()){
            System.out.println(matcher.group(0));
        }
    }

}
