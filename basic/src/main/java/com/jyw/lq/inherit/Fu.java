package com.jyw.lq.inherit;

public class Fu {

    public static void sayHello(){
        System.out.println("I'm Fu");
    }

    public static void sayHellot(){
        sayHello();
    }

    public void tell(Number number){
        System.out.println("tell fu");
    }

}
