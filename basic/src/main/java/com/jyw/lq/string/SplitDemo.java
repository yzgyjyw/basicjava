package com.jyw.lq.string;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SplitDemo {
    public static void main(String[] args) {
        /*String str = "object1.object2.object3";
        String[] split = str.split("\\.");
        Arrays.stream(split).forEach(System.out::println);*/
        String URL_PARAM_WITHQUOTA_FORMAT = "%s&push_msg_id=%s&push_job_key=%s";

        String hybrid_pn = "/abc?1=1";
        Map<String,String> map = new HashMap<>();

        hybrid_pn = String.format(URL_PARAM_WITHQUOTA_FORMAT, hybrid_pn, "messageid",map.get("abc"));

        System.out.println(hybrid_pn);
    }
}