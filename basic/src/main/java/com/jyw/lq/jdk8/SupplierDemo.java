package com.jyw.lq.jdk8;

import java.util.function.Supplier;

public class SupplierDemo {
    //无参数,有返回值
    public static void main(String[] args) {
        Supplier<String> supplier = ()-> "abc";
        System.out.println(supplier.get());
    }
}
