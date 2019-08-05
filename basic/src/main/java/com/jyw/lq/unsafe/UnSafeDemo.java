package com.jyw.lq.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnSafeDemo {



    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        /*//由于Unsafe仅供jdk内部类使用,通过此种方式会抛出异常java.lang.SecurityException: Unsafe
        Unsafe unsafe = Unsafe.getUnsafe();*/

        //通过反射的方式拿到Unsafe
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe)field.get(null);

        //使用unsafe初始化类
        Person person = (Person) unsafe.allocateInstance(Person.class);

        //使用Unsafe实例化类对象的时候，只是给该对象分配内存，不会调用构造方法，因此age为int类型的默认值0
        System.out.println(person.getAge());


        //使用Unsafe修改private字段
        //这就相当于绕过了set或者构造器中的检查，可以将该值设置成任意的int值
        Field ageFiled = person.getClass().getDeclaredField("age");
        unsafe.putInt(person,unsafe.objectFieldOffset(ageFiled),20);
        System.out.println(person.getAge());
    }

}

class Person {
    private int age;

    public Person(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}


