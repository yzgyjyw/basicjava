package com.jyw.lq.effective.generic;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        unsafeAdd(list,1);
        System.out.println(list.get(0));
    }

    public static void unsafeAdd(List list,Object o){
        list.add(o);
    }
}
