package com.jyw.lq.doubleprecision;

public class FloatPrecision {
    public static void main(String[] args) {
        primitive();
        packaging();
    }


    public static  void primitive(){
        float a = 1.0f - 0.9f;
        float b = 0.9f - 0.8f;
        if (a == b) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }

    public static void packaging(){
        Float a = Float.valueOf(1.0f - 0.9f);
        Float b = Float.valueOf(0.9f - 0.8f);
        if (a.equals(b)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}
