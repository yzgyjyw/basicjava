package com.jyw.lq.finallyreturn;

import java.util.HashMap;
import java.util.Map;

/**
 * fianlly语句块中修改了try块中返回的变量的值,try块中返回的值是欧服会改变
 * 需要考虑返回的值是引用类型还是基本类型
 */
public class FinallyUpdateReturnValue {

    public static void main(String[] args) {
//        System.out.println(test3());
//        System.out.println(getMap());
        System.out.println(test3());
    }


    public static int test1() {
        int b = 20;

        try {
            System.out.println("try block");

            return b += 80;
        } catch (Exception e) {

            System.out.println("catch block");
        } finally {

            System.out.println("finally block");

            if (b > 25) {
                System.out.println("b>25, b = " + b);
            }

            //可以fianlly块理解为一个方法,那么b就只是值传递,显然不会影响调用者(try语句块)中b的值
            //虽然在finally中改变了返回值b，但因为finally中没有return该b的值，因此在执行完finally中的语句后，main函数会得到try中返回的b的值
            //而try中的b值依然是程序进入finally代码块前保留下来的值，因此得到的返回值为100。
            b = 150;
        }

        //2000只有在try语句执行到return之前出异常时才会返回
        return 2000;
    }


    public static String test3() {
        String result = "20";

        try {
            System.out.println("try block");
            return result;
        } catch (Exception e) {

            System.out.println("catch block");
        } finally {

            System.out.println("finally block");

            ////可以fianlly块理解为一个方法,那么b就只是值传递,显然不会影响调用者(try语句块)中b的值
            result = "150";
        }

        //2000只有在try语句执行到return之前出异常时才会返回
        return String.valueOf(2000);
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("KEY", "INIT");

        try {
            map.put("KEY", "TRY");
            return map;
        }
        catch (Exception e) {
            map.put("KEY", "CATCH");
        }
        finally {
            //可以fianlly块理解为一个方法,那么map就是引用传递,值是会改变的
            map.put("KEY", "FINALLY");
            map = null;
        }

        return map;
    }


}
