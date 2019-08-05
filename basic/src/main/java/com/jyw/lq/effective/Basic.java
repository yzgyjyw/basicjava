package com.jyw.lq.effective;

import java.math.BigInteger;
import java.util.Random;

public class Basic {
    public static void main(String[] args) {
        Random random = new Random();
        BigInteger bigInteger = BigInteger.probablePrime(9, random);
        System.out.println(bigInteger);

//        Collections.singletonList()
//        Arrays.asList()
//        DriverManager.registerDriver();
//        DriverManager.getConnection();
    }
}
