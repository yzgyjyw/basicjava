package com.jerry.flink.streaming;

import org.apache.commons.lang.StringUtils;

import java.util.Random;

public class NumberUtils {

    private static final String NUMBER = "123456789";

    public static long strToLong(String value) {
        return strToLong(value, 0);
    }

    public static long strToLong(String value, long defaultValue) {
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }

        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
        }
        return defaultValue;
    }

    public static int strToInt(String value) {
        return strToInt(value, 0);
    }

    public static int strToInt(String value, int defaultValue) {
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static float strToFloat(String value) {
        return strToFloat(value, 0);
    }

    public static float strToFloat(String value, float defaultValue) {
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        try {
            return Float.valueOf(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }


    // 将APPID转换为整数,防止每次trim检查浪费性能
    public static long toLong(String idStr) {
        try {
            return Long.parseLong(idStr);
        } catch (Exception e) {
            return Long.parseLong(idStr.trim());    // 如果trim后不是数字,会抛出异常
        }
    }

    /**
     * 生成随机动态码
     *
     * @return
     */
    public static int getRandomNum() {
        Random rand = new Random();
        StringBuilder flag = new StringBuilder();
        for (int j = 0; j < 6; j++) {
            flag.append(NUMBER.charAt(rand.nextInt(9)));
        }
        return Integer.parseInt(flag.toString());
    }
}
