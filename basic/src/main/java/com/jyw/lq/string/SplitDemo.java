package com.jyw.lq.string;

import java.util.Arrays;

public class SplitDemo {
    public static void main(String[] args) {
        String str = "object1.object2.object3";
        String[] split = str.split("\\.");
        Arrays.stream(split).forEach(System.out::println);
    }
}