package com.jyw.lq.collect;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MapRemove {

    public static void main(String[] args) {
        Map<Long, Long> map = new ConcurrentHashMap<>();
        map.put(1L,2L);
        map.put(2L,3L);


        for(Map.Entry<Long, Long> entry : map.entrySet()){
            System.out.println(entry.getKey() + "\t" + entry.getValue());
            map.remove(entry.getKey());
        }

        System.out.println(map.size());
    }

}
