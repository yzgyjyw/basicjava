package com.jyw.lq.retry;

public class RetryDemo {
    public static void main(String[] args) {
        //retry不是关键字，可以替换为任意的符合规范的字符串
//        retry:
        for (int i = 0; i < 10; i++) {
            retry:
            while (i == 5) {
//                continue retry;
//                break retry;
            }
            System.out.print(i + " ");
        }
    }
}
