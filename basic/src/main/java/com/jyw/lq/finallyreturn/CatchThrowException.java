package com.jyw.lq.finallyreturn;

// catch代码块捕获了异常但是又抛出了异常,那么finally块中抛出的是catch代码块中抛出的异常
public class CatchThrowException {

    public static void main(String[] args) {

        try{
            int a = 0;
            int b = 1 / a;
        }catch(ArithmeticException ex){
            throw new ArrayIndexOutOfBoundsException(1);
        }finally {
//            throw new RuntimeException();
        }

    }


}
