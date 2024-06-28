package com.jyw.lq.string;


import java.util.*;

public class GuavaSplit {
    public static void main(String[] args) {
       /* int a = 0;
        int b = 0;
        int c = a==0 ? b==0 ? 1 :2
                : b==0 ? 3 : 4;
        System.out.println(c);*/


        /*List<String> abc = Splitter.on(",").splitToList("abc");

        System.out.println(abc.size());

        System.out.println(abc.get(0));

        String s = Joiner.on(",").join("", "", 0);
        System.out.println(s);

        Map<String,String> map = new HashMap();

        Optional<Map.Entry<String, String>> first = map.entrySet().stream().filter(kv -> kv.getKey() != null).findFirst();

        System.out.println(1);

        List<String> list = new ArrayList<>();
        list.add("");

        list.add("a");
        list.add("b");
        List<String> collect = list.stream().filter(str -> str == "").collect(Collectors.toList());
        System.out.println(collect.size());*/

        Map<String, Integer> env2Quota = new HashMap<>();
        env2Quota.put("1",1);
        env2Quota.put("2",2);
        env2Quota.put("3",3);
        System.out.println(env2Quota.values().stream().reduce((i1, i2) -> i1+i2).orElse(0));

    }


}
