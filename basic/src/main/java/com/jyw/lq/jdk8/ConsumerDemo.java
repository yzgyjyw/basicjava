package com.jyw.lq.jdk8;

import java.util.function.Consumer;

public class ConsumerDemo {
    /*//Consumer接收一个参数,不返回任何值
    public static void main(String[] args) {
        Consumer<String> strConsumer = str-> System.out.println(str.toUpperCase());

        strConsumer.accept("hello");

        Consumer<String> andThen = strConsumer.andThen(str -> System.out.println(str.toLowerCase()));

        andThen.accept("Hello");

    }*/
}
