package com.jyw.lq.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionsFunc {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(3);
        list.add(4);
        int frequency = Collections.frequency(list, 1);
        System.out.println(frequency);
    }
}
