package com.jyw.lq.generic;

import java.util.ArrayList;
import java.util.List;

// producer的意思是可以从集合中get元素，但是不能add元素
// consumer的意思是可以add元素到集合中，get出来的元素都是Object类型的
public class PECS {

    public static void main(String[] args) {

        //意思是可以将List<Apple>赋值给eList
        List<? extends Fruit> eList = new ArrayList<>();
        //java无法判断eList究竟是什么类型的，假如这个eList是一个List<Orange>,这边加一个apple，显然是不合理的，因此直接禁止加入元素到eList
//        eList.add(new Fruit("apple1"));



        //意思是可以将ArrayList<Fruit>,ArrayList<Object>赋值给list,但是不能将ArrayList<Apple>赋值给list
        List<? super Fruit> list = new ArrayList<>();
        list.add(new Apple("apple1"));
        list.add(new Fruit("fruit1"));

        //取出来的每一个元素都是object类型的
        Object object = list.get(0);
    }

}

class Fruit{
    private String name;

    public Fruit(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "name='" + name + '\'' +
                '}';
    }
}

class Apple extends Fruit{

    private String name;

    public Apple(String name) {
        super(name);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "name='" + name + '\'' +
                '}';
    }
}
