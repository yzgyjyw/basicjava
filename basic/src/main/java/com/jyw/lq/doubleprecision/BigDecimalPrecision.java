package com.jyw.lq.doubleprecision;

import java.math.BigDecimal;

public class BigDecimalPrecision {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal(0.1);

        //使用 double 传参的时候会产生不可预期的结果，比如说 0.1 实际的值是 0.1000000000000000055511151231257827021181583404541015625
        System.out.println(a);//0.1000000000000000055511151231257827021181583404541015625

        //使用字符串传参的时候会产生预期的结果，比如说 new BigDecimal("0.1") 的实际结果就是 0.1
        BigDecimal b = new BigDecimal("0.1");//0.1
        System.out.println(b);

        //如果必须将一个 double 作为参数传递给 BigDecimal 的话，建议传递该 double 值匹配的字符串值。方式有两种：
        /**
         * double a = 0.1;
         * System.out.println(new BigDecimal(String.valueOf(a))); // 0.1
         * System.out.println(BigDecimal.valueOf(a)); // 0.1
         */

        boolean c = 1 > 2 && 1 != 0;
        System.out.println(c);
    }
}
