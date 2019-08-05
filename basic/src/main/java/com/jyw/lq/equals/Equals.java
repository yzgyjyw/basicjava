package com.jyw.lq.equals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Equals {
    public static void main(String[] args) {
        List<String> aList = new ArrayList<>();
        aList.add("abc");
        aList.add("bcd");

        List<String> lList = new LinkedList<>();
        lList.add("abc");
        lList.add("bcd");

        System.out.println(aList.equals(lList));
    }
}
