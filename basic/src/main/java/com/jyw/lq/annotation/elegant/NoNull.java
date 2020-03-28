package com.jyw.lq.annotation.elegant;

import org.checkerframework.checker.nullness.qual.NonNull;

public class NoNull {
    public static void main(String[] args) {

        System.out.println(concatString(null,"21"));

        System.out.println(1);

    }

    public static String concatString(@NonNull String name,@NonNull String age){
        return name.concat("\tage:\t").concat(age);
    }

}
