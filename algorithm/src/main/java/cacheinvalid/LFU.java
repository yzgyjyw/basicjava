package cacheinvalid;

import java.util.*;

public class LFU {

    public static void main(String[] args) {

        LfuCache lfuCache = new LfuCache(3);

        lfuCache.add("1","a");

        lfuCache.add("2","b");

        System.out.println(lfuCache.get("1"));

        lfuCache.add("3","c");

        lfuCache.add("4","d");

        System.out.println(lfuCache.get("2"));



    }


    static class CacheItem implements Comparable<CacheItem> {

        private String key;
        private String value;

        private int count;

        private long time;

        public void setCount(int count) {
            this.count = count;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public CacheItem(String key, String value, int count, long time) {
            this.key = key;
            this.value = value;
            this.count = count;
            this.time = time;
        }


        @Override
        public int compareTo(CacheItem o) {
            int compare = Integer.compare(this.count, o.count);

            if (compare == 0) {
                compare = Long.compare(o.time,this.time);
            }

            return compare;
        }

        @Override
        public String toString() {
            return "CacheItem{" +
                    "key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    ", count=" + count +
                    ", time=" + time +
                    '}';
        }
    }


    static class LfuCache {
        private int size;

        private Map<String, CacheItem> map;

        private PriorityQueue<CacheItem> queue;

        public LfuCache(int size) {
            this.size = size;
            map = new HashMap<>();
            queue = new PriorityQueue<>();
        }

        public void add(String key, String value) {

            CacheItem cacheItem = get(key);

            if (cacheItem != null) {
                return;
            }

            if (queue.size() >= size) {
                CacheItem removedItem = queue.remove();
                map.remove(removedItem.key);
            }

            CacheItem item = new CacheItem(key, value, 1, System.currentTimeMillis());
            queue.add(item);
            map.put(key, item);
        }

        public CacheItem get(String key) {
            CacheItem cacheItem = map.get(key);

            if (cacheItem != null) {
                cacheItem.setCount(cacheItem.count + 1);
                cacheItem.setTime(System.currentTimeMillis());

            }

            return cacheItem;
        }
    }
}
