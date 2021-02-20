package cacheinvalid;

import java.util.*;

public class LRU {

    public static void main(String[] args) {

        LRUCache lruCache = new LRUCache(3);

        lruCache.put("a", "1");
        lruCache.put("b", "2");
        lruCache.put("c", "3");
        lruCache.put("d", "4");

        System.out.println(lruCache.get("a"));

        System.out.println(lruCache.get("d"));

        lruCache.put("e", "5");

        System.out.println(lruCache.get("d"));

        System.out.println(lruCache.get("b"));


    }


    static class LRUCache {

        private int size;

        private Map<String, String> map;

        private List<String> list;

        public LRUCache(int size) {
            this.size = size;
            this.map = new HashMap<>();
            this.list = new LinkedList<>();
        }

        public void put(String key, String value) {

            if (get(key) != null) return;

            map.put(key, value);
            list.add(0, key);

            if (list.size() > size) {
                String removedKey = list.remove(list.size() - 1);
                map.remove(removedKey);
            }
        }

        public String get(String key) {
            String result = map.get(key);
            if (result != null) {
                list.remove(key);
                list.add(0, key);
            }

            return result;
        }
    }

}
