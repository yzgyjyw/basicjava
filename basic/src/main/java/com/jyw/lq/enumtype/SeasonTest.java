package com.jyw.lq.enumtype;

/**
 * enum中的每一个实例都是一个public static final类型的字段,在该类被初始化时都会调用相应的构造函数初始化实例
 */
public class SeasonTest {
    public static void main(String[] args) {
        System.out.println(Season.AUTUMN);
        for(Season s : Season.values()){

        }
    }
}
