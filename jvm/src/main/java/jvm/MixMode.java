package jvm;

import java.util.HashMap;
import java.util.Map;

public class MixMode {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Map<String, String> map = new HashMap<String, String>();
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3");
        map.put("k4", "v4");
        map.put("k5", "v5");
        map.put("k6", "v6");
        map.put("k7", "v7");
        map.put("k8", "v8");
        map.put("k9", "v9");
        map.forEach((k, v) -> System.out.println(k + "----" + v));
        System.out.println(System.currentTimeMillis() - start);
    }
}
