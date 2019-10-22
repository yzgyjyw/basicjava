package com.jyw.lq.string;

public class Intern {
    public static void main(String[] args) {

        String str1 = "abc";

        String str2 = new String("abc");

        String str3 = str2.intern();

        System.out.println(str1 == str2);
        System.out.println(str2 == str3);
        System.out.println(str1 == str3);

        String a =new String("abc").intern();

        String b = new String("abc").intern();



        if(a==b) {

            System.out.print("a==b");

        }
    }
}
