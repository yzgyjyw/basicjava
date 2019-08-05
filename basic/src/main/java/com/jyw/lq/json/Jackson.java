package com.jyw.lq.json;

public class Jackson {

    public static void main(String[] args) {



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
