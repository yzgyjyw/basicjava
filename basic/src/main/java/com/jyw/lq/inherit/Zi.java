package com.jyw.lq.inherit;

public class Zi extends Fu {

    public static void sayHello(){
        System.out.println("I'm Zi");
    }

    @Override
    public void tell(Number i){
        System.out.println("tell zi");
    }

}
