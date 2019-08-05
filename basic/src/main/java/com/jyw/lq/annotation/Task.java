package com.jyw.lq.annotation;

public class Task {

    @TODOAnnotation(priority = TODOAnnotation.Priority.high)
    public static void task1() {
        System.out.println("task1 is assigend");
    }

    @TODOAnnotation(author = "lq")
    public static void task2(){
        System.out.println("task2 is assigend");
    }

    @TODOAnnotation
    public static void task3(){
        System.out.println("task3 is assigend");
    }

}
