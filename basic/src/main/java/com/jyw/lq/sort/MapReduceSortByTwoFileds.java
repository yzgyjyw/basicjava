package com.jyw.lq.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapReduceSortByTwoFileds {

    public static void main(String[] args) {
        List<Sample> list = new ArrayList<>();

        list.add(new Sample("jyw",10));
        list.add(new Sample("jyw",10));
        list.add(new Sample("jyw3",8));
        list.add(new Sample("jyw4",9));
        list.add(new Sample("jyw5",13));
        list.add(new Sample("jyw6",12));
        list.add(new Sample("jyw",11));
        list.add(new Sample("jyw2",10));
        list.add(new Sample("jyw1",10));
        Collections.sort(list);

        list.forEach(System.out::println);
    }



}


class Sample implements Comparable<Sample>{
    String name;
    int age;

    public Sample(String name, int age) {
        this.name = name;
        this.age = age;
    }


    @Override
    //先按照年龄比较，然后再按照姓名比较
    public int compareTo(Sample o) {
        int comp = this.age - o.age;
        if(comp==0){
            return this.name.compareTo(o.name);
        }
        return comp;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}