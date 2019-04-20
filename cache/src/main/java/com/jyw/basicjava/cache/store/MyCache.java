package com.jyw.basicjava.cache.store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义cache，主要包括增加，查询，删除功能
 * 其实也可以直接使用MyCache，但是有些情况下可能我们的应用中可能会有多个Cache，此时无法对这些Cache进行管理
 * @param <K>
 * @param <V>
 */
public class MyCache<K,V> {
    private DataStore<K,V> dataStore;

    private Logger logger = LoggerFactory.getLogger(MyCache.class);

    public MyCache(final DataStore<K, V> dataStore) {
        this.dataStore = dataStore;
    }

    public V get(K key){
        ValueHolder<V> valueHolder = null;
        try {
            valueHolder = dataStore.get(key);
        } catch (StoreAccesException e) {
            logger.error(e.getMessage());
        }
        if(valueHolder!=null){
            return valueHolder.value();
        }
        return null;
    }

    public void put(K key,V value){
        try {
            dataStore.put(key,value);
        } catch (StoreAccesException e) {
            e.printStackTrace();
        }
    }

    public void remove(K key){
        try {
            dataStore.remove(key);
        } catch (StoreAccesException e) {
            e.printStackTrace();
        }
    }

    public void clear(){
        try {
            dataStore.clear();
        } catch (StoreAccesException e) {
            e.printStackTrace();
        }
    }
}
