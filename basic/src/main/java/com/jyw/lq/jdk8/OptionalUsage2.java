package com.jyw.lq.jdk8;

import java.util.Optional;

public class OptionalUsage2 {

    public static void main(String[] args) {
        System.out.println(Optional.ofNullable("1").orElseGet(()->{
            return "abc";
        }));

        System.out.println(Optional.ofNullable(null).orElseGet(()->{
            return "abc";
        }));
    }


}
