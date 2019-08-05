package com.jyw.lq.jdk8.collection;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

//map类型不支持stream，不过jdk8中还是为map添加了一些新的方法
public class MapDemo {
    private static Map<Integer,String>  initMap(){
        return Arrays.stream("jyw is a very good student".split(" ")).collect(Collectors.toMap(str->str.length(),str->str,(ov,nv)->ov+","+nv));
    }

    public static void main(String[] args) {
        Map<Integer, String> map = initMap();
        map.putIfAbsent(5,"yzgyl");
        //如果当前的key存在，则执行后面的逻辑，并且返回执行后的结果
        String s = map.computeIfPresent(5, (key, value) -> key + value);
        //不存在则不会执行定义好的方法并且返回null
        String s3 = map.computeIfPresent(6, (key, value) -> key + value);

        //如果当前key不存在，则将指定的值插入到map中
        String s1 = map.computeIfAbsent(6, key -> key + "12345");
        //因为6已经存在，那么返回的就是map.get(6)
        String s2 = map.computeIfAbsent(6, key -> key + "123456");

        //如果当前存在键值6，那么使用指定函数对值进行操作，否则就往map中插入value
        map.merge(6,"666",(ov,nv)->ov+nv);

        //根据指定的key和value删除元素
        map.remove(1,"a");

        System.out.println(s);
    }
}
