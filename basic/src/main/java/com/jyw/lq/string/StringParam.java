package com.jyw.lq.string;

import java.util.HashSet;
import java.util.Set;

public class StringParam {
    public static void main(String[] args) {
        String packageName = "packageName";
        updateString(packageName);
        System.out.println(packageName);

        Set<String> set = new HashSet<>();
//        set.add("abc")
        System.out.println(set.contains(""));

    }

    private static String updateString(String packageName){
        packageName = "new PackageName";
        if(packageName=="packageName"){
            throw new RuntimeException("runtimeException");
        }
        return "pack";
    }



}
