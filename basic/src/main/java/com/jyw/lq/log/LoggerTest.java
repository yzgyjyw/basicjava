package com.jyw.lq.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class LoggerTest {
    private static Logger LOGGER = LoggerFactory.getLogger(LoggerTest.class);

    static boolean boolValue;

    public static void main(String[] args) {
        Integer ttl = null;
        if(ttl==null || ttl==0){
            System.out.println("ok");
        }


        //2:10
        boolValue = Boolean.valueOf("0");
        System.out.println(boolValue);

        try {
            useList();
        } catch (Exception e) {
            Arrays.stream(e.getStackTrace()).forEach(s->LOGGER.info(s.toString()));
            LOGGER.error("error e ,{}","abc" ,e);
        }
    }

    private static List<String> getArraylist(){
        return null;
    }

    private static void useList(){
        getArraylist().contains("a");
    }

    private static FileInputStream getFile(String abc) throws FileNotFoundException {
        return new FileInputStream("/home/mi/unknown.txt");
    }


    private static FileInputStream getFile() throws FileNotFoundException {
        return getFile("");
    }

}
