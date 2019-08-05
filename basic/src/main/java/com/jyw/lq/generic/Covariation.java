package com.jyw.lq.generic;


public class Covariation {

    public static void main(String[] args) {
        //数组是协变的 true
        System.out.println(Number[].class.isAssignableFrom(Integer[].class));
    }
}
