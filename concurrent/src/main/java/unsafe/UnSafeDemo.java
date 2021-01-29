package unsafe;

import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class UnSafeDemo {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
//        allocateMemory();
        unsafeWithArray();
    }

    public static void likeReflect() throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        /*//由于Unsafe仅供jdk内部类使用,通过此种方式会抛出异常java.lang.SecurityException: Unsafe
        Unsafe unsafe = Unsafe.getUnsafe();*/

        //通过反射的方式拿到Unsafe
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);

        //使用unsafe初始化类
        Person person = (Person) unsafe.allocateInstance(Person.class);

        //使用Unsafe实例化类对象的时候，只是给该对象分配内存，不会调用构造方法，因此age为int类型的默认值0
        System.out.println(person.getAge());


        //使用Unsafe修改private字段
        //这就相当于绕过了set或者构造器中的检查，可以将该值设置成任意的int值
        Field ageFiled = person.getClass().getDeclaredField("age");
        unsafe.putInt(person, unsafe.objectFieldOffset(ageFiled), 20);
        System.out.println(person.getAge());
    }

    // 抛出checked异常不用trycatch或者在方法声明中抛出
    public static void throwCheckedException() throws NoSuchFieldException, IllegalAccessException {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        //使用unsafe抛出的异常可以不用try catch或者在方法声明中throws
        unsafe.throwException(new IOException());
    }

    // 声明堆外内存
    // 注意此时的内存声明是在堆外申请的,不会由GC进行回收,需要自己手动调用freeMemory回收内存
    public static void allocateMemory() throws NoSuchFieldException, IllegalAccessException {
        int INT = 10;
        // 单位字节
        long size = 4;


        Unsafe unsafe;
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        unsafe = (Unsafe) f.get(null);

        // 申请40个字节的空间
        long address = unsafe.allocateMemory(size * INT);

        //为40个字节的空间赋值
        for (int i = 0; i < INT; i++) {
            unsafe.putInt(address + i * size, i);
        }

        //取出40个字节的空间的值
        for (int i = 0; i < INT; i++) {
            System.out.println(unsafe.getInt(address + i * size));
        }

        //最后记得释放申请到的内存
        unsafe.freeMemory(address);

        System.out.println("after free ...");
        //取出40个字节的空间的值
        for (int i = 0; i < INT; i++) {
            System.out.println(unsafe.getInt(address + i * size));
            //0 0 2 3 4 5 6 7 8 9 释放后内存的值,类似于C语言free后,内存中的是随机的值
        }
    }

    // park和unpark

    /**
     * JVM在上下文切换的时候使用了Unsafe中的两个非常牛逼的方法park()和unpark()。
     * 当一个线程正在等待某个操作时，JVM调用Unsafe的park()方法来阻塞此线程。
     * 当阻塞中的线程需要再次运行时，JVM调用Unsafe的unpark()方法来唤醒此线程。
     * 我们之前在分析java中的集合时看到了大量的LockSupport.park()/unpark()，它们底层都是调用的Unsafe的这两个方法。
     */

    public static void unsafeWithArray() throws NoSuchFieldException, IllegalAccessException {
        String[] str = new String[5];

        String a1 = "1";
        String a2 = "2";
        String a3 = "3";
        String a4 = "4";
        String a5 = "5";

        str[0] = a1;
        str[1] = a2;
        str[2] = a3;
        str[3] = a4;



        Unsafe unsafe;
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        unsafe = (Unsafe) f.get(null);

        Arrays.stream(str).forEach(i -> System.out.println(i));

        //获取数组头位置
        int base = unsafe.arrayBaseOffset(String[].class);

        //获取数组偏移量
        int scale = unsafe.arrayIndexScale(String[].class);

        // 修改数组的0号元素
        unsafe.compareAndSwapObject(str, base + 0 * scale, a1, "10");

        Arrays.stream(str).forEach(i -> System.out.println(i));
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


