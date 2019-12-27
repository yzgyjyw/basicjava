package com.jyw.lq.string;

import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Intern {
    public static void main(String[] args) {

        /*String str1 = "abc";

        String str2 = new String("abc");

        String str3 = str2.intern();

        System.out.println(str1 == str2);
        System.out.println(str2 == str3);
        System.out.println(str1 == str3);

        String a =new String("abc").intern();

        String b = new String("abc").intern();



        if(a==b) {

            System.out.print("a==b");

        }*/

        List<Long> uuids = new ArrayList<>();
        uuids.add(1L);
        uuids.add(30L);
        uuids.add(0L);
        uuids.sort(Long::compareTo);
        for (int i = 0; i < uuids.size() - 1; i++) {
            System.out.println(uuids.get(i));
        }
        System.out.println("yYYYYYYYYYYYYY");
        System.out.println(uuids.get(uuids.size() - 1));

    }
}
