package com.jyw.lq.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayAndGeneric {

    public static void main(String[] args) {
//        test1();

//        test2();

        test3();
    }


    public static void test1()
    {
        SubClass[] subArray = {new SubClass(), new SubClass()};
        System.out.println(subArray.getClass());

        // class [Lcollection.SubClass;
        BaseClass[] baseArray = subArray;
        System.out.println(baseArray.getClass());

        // java.lang.ArrayStoreException
        baseArray[0] = new BaseClass();
    }

    public static void test2()
    {
        List<String> list = Arrays.asList("abc");

        // class java.util.Arrays$ArrayList
        System.out.println(list.getClass());

        // class [Ljava.lang.String;
        Object[] objArray = list.toArray();
        System.out.println(objArray.getClass());

        objArray[0] = new Object(); // cause ArrayStoreException
    }

    public static void test3()
    {
        List<String> dataList = new ArrayList<>();
        dataList.add("one");
        dataList.add("two");

        Object[] listToArray = dataList.toArray();

        // class [Ljava.lang.Object;返回的是Object数组
        System.out.println(listToArray.getClass());
        listToArray[0] = "";
        listToArray[0] = 123;
        listToArray[0] = new Object();

    }

    private static class SubClass extends BaseClass{
    }

    private static class BaseClass {
    }
}
