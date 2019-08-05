package com.jyw.lq.jdk8;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.function.Function;

public class FunctionDemo {
    public static void main(String[] args) {

        Map map = new HashMap<>();
        System.out.println(map.get("abc"));

        //Function<T, R>  函数式接口     T:参数类型,R:返回值类型
        Function<Integer, String> int2String = x -> x.toString();
        //使用lambda表达式实现函数式接口
        Function<Integer, Integer> opInt = new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer + 2;
            }
        };


        String str = int2String.apply(2);
        System.out.println(str);


        //Function函数式接口的andThen
        Function<Integer, Integer> andThen = int2String.andThen(strValue -> strValue.length());
        System.out.println(andThen.apply(100));

        //Function函数式接口的compose
        Function<String, String> compose = int2String.compose((String name) -> {
            if (name.equals("abc")) {
                return 7;
            } else {
                return 9;
            }
        });

        System.out.println(compose.apply("abc"));

        CountDownLatch countDownLatch  = new CountDownLatch(10);
        countDownLatch.countDown();

    }


}
