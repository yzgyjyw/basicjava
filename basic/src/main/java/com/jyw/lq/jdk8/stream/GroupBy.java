package com.jyw.lq.jdk8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupBy {
    public static void main(String[] args) {
        String[] words={"bac","abc","qwe","ewq","wqe"};
        Map<String, List<String>> collect = Arrays.stream(words).collect(Collectors.groupingBy(GroupBy::alphabetize));
        System.out.println(collect);

        Stream<String> stringStream = Arrays.stream(words).flatMap(word -> Stream.of(words));

        List<String> collect1 = stringStream.collect(Collectors.toList());
        System.out.println(collect1);

    }

    public static String alphabetize(String word){
        char[] a = word.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }
}
