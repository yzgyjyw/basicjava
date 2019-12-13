package com.jyw.lq.json;

import java.util.HashMap;
import java.util.Map;

public class Jackson {

    public static void main(String[] args) {

        Map<String ,Integer> map = new HashMap<>();
        Integer value  = 100000;
        value = map.getOrDefault("1",value);
        System.out.println(value);

    }



    private static class QuickAppInfo{
        private AppInfo appInfo;
    }

    private static class Source{
        private String packageName;
        private String type;
    }


    private static class AppInfo{
        private String icon;
        private Source source;
    }


}
