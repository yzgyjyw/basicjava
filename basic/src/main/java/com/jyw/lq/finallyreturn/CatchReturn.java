package com.jyw.lq.finallyreturn;

public class CatchReturn {
    public static void main(String[] args) {
        System.out.println(getToken());
    }

    public static Boolean getToken() {
        try {
            int a = 1 / 0;
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
