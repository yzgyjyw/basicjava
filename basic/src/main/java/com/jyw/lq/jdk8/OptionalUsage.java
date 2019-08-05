package com.jyw.lq.jdk8;

import javax.xml.ws.FaultAction;
import java.util.List;
import java.util.Optional;

public class OptionalUsage {
    public static void main(String[] args) {
//        System.out.println(validate8(null));
//        System.out.println(validate8("abc"));
        System.out.println(validate8("bcd"));
    }


    private static void validate(){

    }

    private static boolean validateUser(String name){
        return name.startsWith("a");
    }

    /*private static String validate7(String name){

    }*/
    private static String validate8(String name){
        return Optional.ofNullable(name).filter(OptionalUsage::validateUser).orElseThrow(()->new IllegalArgumentException("ivalida username"));
    }


    private static void getPhoneModel(){
        System.out.println(getPhoneModel7(null));
        System.out.println(getPhoneModel8(null));

        Brand brand = new Brand();
        Factory factory = new Factory();
        Model model = new Model();
        System.out.println(getPhoneModel7(brand));
        System.out.println(getPhoneModel8(brand));

        brand.factory = factory;
        System.out.println(getPhoneModel7(brand));
        System.out.println(getPhoneModel8(brand));

        factory.model = model;
        System.out.println(getPhoneModel7(brand));
        System.out.println(getPhoneModel8(brand));

        model.name = "MI9";
        System.out.println(getPhoneModel7(brand));
        System.out.println(getPhoneModel8(brand));
    }


    private static String getPhoneModel7(Brand brand){
        if(brand!=null){
            Factory factory = brand.factory;
            if(factory!=null){
                Model model = factory.model;
                if(model!=null){
                    return model.name;
                }
            }
        }
        return null;
    }

    private static String getPhoneModel8(Brand brand){
        return Optional
                .ofNullable(brand)
                .map(b->brand.factory)
                .map(f->f.model)
                .map(m->m.name)
                .orElse(null);
    }
}


class Brand{
    Factory factory;
    String name;
}

class Factory{
    Model model;
    String name;
}

class Model{
    String name;
}
