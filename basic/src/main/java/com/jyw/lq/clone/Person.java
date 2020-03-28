package com.jyw.lq.clone;

import java.util.Arrays;

public class Person implements Cloneable {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public static void main(String[] args) throws CloneNotSupportedException {

        //基本类型数组clone
        primitiveClone();

        //对象clone，浅拷贝
        objectClone();

        //true 数组的clone为浅拷贝
        objectArrayClone();
    }

    private static void primitiveClone() {
        int[] array = new int[10];
        for (int i = 0; i < 10; i++) {
            array[i] = i;
        }
        int[] clone1 = array.clone();
        clone1[0] = 100;
        //基本数据类型的数组的clone是ok的,clone[0] != array[0]
        System.out.println(array[0]);
    }

    private static void objectClone() throws CloneNotSupportedException {
        Person person = new Person();
        person.setAge(20);
        person.setName("jyw");
        Object clone = person.clone();
        System.out.println(clone == person);
    }

    private static void objectArrayClone(){
        //对象数组clone 浅拷贝
        Person[] persons = new Person[10];
        for (int i = 0; i < 10; i++) {
            persons[i] = new Person();
        }
        Person[] clone2 = persons.clone();
        // false
        System.out.println(clone2 == persons);
        //true 数组的clone为浅拷贝
        System.out.println(clone2[0] == persons[0]);
    }
}
