package com.jyw.lq.inherit;

public class Main {
    public static void main(String[] args) {
        //子类中重写了父类的静态方法，认为是覆盖
        Zi.sayHello();
        //静态方法不存在多态的说法
        Zi.sayHellot();
    }
}
