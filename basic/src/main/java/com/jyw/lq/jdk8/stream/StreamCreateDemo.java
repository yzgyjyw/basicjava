package com.jyw.lq.jdk8.stream;

import java.util.Arrays;
import java.util.stream.Stream;

public class StreamCreateDemo {

    public static void main(String[] args) {
       unLimitStream();
       limitStream();
    }

    //创建包含无限个元素的流
    private static void unLimitStream(){
        //使用generate
        Stream<String> c = Stream.generate(()->"c");
        //c.forEach(System.out::println);
        Stream.generate(Math::random);

        //使用iterate迭代生成无限元素
        Stream<Integer> integerStream = Stream.iterate(1, a -> a + 1);
//        integerStream.forEach(System.out::println);
    }

    private static void limitStream(){
        //从数组生成流
        Stream<String> stringStream = Stream.of("c");
        String words = "jyw is a good student";
        Stream.of(words.split(" "));

        String[] wordsArray = words.split(" ");
        Arrays.stream(wordsArray);
    }

}
