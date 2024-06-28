package com.jyw.lq.systemproperty;

public class Main {

    public static void main(String[] args) {
        // 0000000000000000001
        System.out.println(Integer.toBinaryString(-1));
        // 11111111111111111111111111100000  11111 100000
        System.out.println(-1 << 5);

        System.out.println(Integer.toBinaryString(-1 << 5));
        System.out.println(Integer.toBinaryString(-1 << 5).length());
    }

}
