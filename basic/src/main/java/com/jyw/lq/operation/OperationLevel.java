package com.jyw.lq.operation;

public class OperationLevel {

    public static void main(String[] args) {
        int t = 1;
        int tail = 2;
        int p = 1;
        int q = 4;
        p = (p != t && t != (t = tail)) ? t : q;

        System.out.println(t);
    }
}
