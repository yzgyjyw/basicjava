package com.jyw.lq.jdk8.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamCollectDemo {
    public static void main(String[] args) {
        collectToArray();
//        collectToList();
//        collectToSet();
//        collectToString();
//        collectToSummaryStatistics();
//        collectToMap();
    }

    private static void collectToArray(){
        String[] wordsArray = "jyw is a good student".split(" ");

        //注意由于无法在运行时创建泛型数组，所以默认的情况下生成的数组是object[]数组
        //要想生成指定类型的数组，必须将类型的构造函数传递过去
        String[] strings = Arrays.stream(wordsArray).toArray(String[]::new);
    }


    private static void collectToList() {
        String[] wordsArray = "jyw is a good student".split(" ");
        Stream.of(wordsArray).collect(Collectors.toList());

    }

    private static void collectToSet() {
        String[] wordsArray = "jyw is a good student".split(" ");
        Stream.of(wordsArray).collect(Collectors.toSet()).forEach(System.out::println);

        //指定set的类型
        TreeSet<String> treeSet = Stream.of(wordsArray).collect(Collectors.toCollection(TreeSet::new));
        treeSet.forEach(System.out::println);
    }

    private static void collectToString() {
        String[] wordsArray = "jyw is a good student".split(" ");
        String s = Stream.of(wordsArray).collect(Collectors.joining(","));
        System.out.println(s);
    }

    private static void collectToSummaryStatistics(){
        String[] wordsArray = "jyw is a good student".split(" ");
        IntSummaryStatistics iss = Arrays.stream(wordsArray).collect(Collectors.summarizingInt(String::length));
        System.out.println(iss.getMax());
        System.out.println(iss.getAverage());
        System.out.println(iss.getCount());
        System.out.println(iss.getMin());
    }

    private static void collectToMap(){
        //没有重复键值
        String[] wordsArray = "jyw is a good student".split(" ");
        Arrays.stream(wordsArray).collect(Collectors.toMap(String::length,str->str)).forEach((length,str)-> System.out.println(length+","+str));

        //有重复键值，不处理exception java.lang.IllegalStateException: Duplicate key very
        String[] wordsArray2 = "jyw is a very good student".split(" ");
//        Arrays.stream(wordsArray2).collect(Collectors.toMap(String::length,str->str)).forEach((length,str)-> System.out.println(length+","+str));

        //产生冲突时，只保留原有的value，newValue被丢弃
        Stream.of(wordsArray2).collect(Collectors.toMap(String::length,str->str,(oldValue,newValue)->oldValue)).forEach((length,str)-> System.out.println(length+","+str));

        //使用Set记录value值

        Map<Integer, Set<String>> collect = Stream.of(wordsArray2).collect(Collectors.toMap(String::length, str -> Collections.singleton(str), (ov, nv) -> {
            Set<String> set = new HashSet<>(ov);
            set.addAll(nv);
            return set;
        }));

        System.out.println(1);


    }



}
