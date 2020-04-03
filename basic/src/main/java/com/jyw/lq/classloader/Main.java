package com.jyw.lq.classloader;

public class Main {
    public static void main(String[] args) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println(loader);
        ClassLoader parentLoader = loader.getParent();
        System.out.println(parentLoader);
        System.out.println(parentLoader.getParent());
    }
}
