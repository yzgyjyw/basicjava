package com.jyw.basicjava.cache.store;

/**
 * 存储对象的接口
 * @param <K>
 * @param <V>
 */
public interface DataStore<K,V> {
    ValueHolder<V> get(K key) throws StoreAccesException;

    PUTSTATUS put(K key,V value) throws StoreAccesException;

    ValueHolder<V> remove(K key) throws StoreAccesException;

    void clear() throws StoreAccesException;

    enum PUTSTATUS{
        PUT,
        UPDATE,
        ERROR
    }
}
