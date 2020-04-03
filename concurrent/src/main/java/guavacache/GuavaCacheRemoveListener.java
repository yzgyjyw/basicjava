package guavacache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GuavaCacheRemoveListener {

    public static void main(String[] args) throws InterruptedException {
        Cache<Long, Map<String, String>> localCache = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .removalListener(new RemovalListener<Long, Map<String, String>>() {
                    @Override
                    public void onRemoval(RemovalNotification<Long, Map<String, String>> notification) {
                        System.out.println("remove\t" + notification.getKey() + "\t"+notification.getValue());
                    }
                }).build();


        localCache.put(1L, Maps.newHashMap());

        Thread.sleep(2000);
        localCache.put(2L,Maps.newHashMap());

        System.out.println("end");

    }

}
