package com.jyw.lq.jdk8;

import java.util.function.Predicate;

public class PredicateDemo {
    //接收一个参数,返回true或者false
    public static void main(String[] args) {
        Predicate<Integer> testIsNatureNumber = i-> i>=0;

        //true
        System.out.println(testIsNatureNumber.test(10));

        //取反 false
        System.out.println(testIsNatureNumber.negate().test(10));

        //and true
        System.out.println(testIsNatureNumber.and(i->i<=10).test(10));

        //or true
        System.out.println(testIsNatureNumber.or(i->i>=-1).test(-1));
    }
}
