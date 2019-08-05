package com.jyw.lq.generic;

import java.util.ArrayList;
import java.util.List;

public class UnrestrictedWildcard {
    public static void main(String[] args) {
        //无限制通配符与原声类型的区别：
        /*
        * 无限制通配符只能添加null元素，确保了类型安全
        * 原生类型则可以添加任意元素，无法保证类型安全
        *
        * 不要使用原生类型，它们至于存在泛型之前的代码作兼容处理
        *
        * 使用原生类型的地方：在使用类字面值的时候(即使用泛型类的class对象的地方)
        * 因为List.class String[].class,int.class是合法的，但是List<String>.class和List<?>.class是不合法的
        *
        * */
        List<?> list = new ArrayList<>();
//        list.add("abc");
    }
}
