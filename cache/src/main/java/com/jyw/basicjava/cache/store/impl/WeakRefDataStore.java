package com.jyw.basicjava.cache.store.impl;

import com.jyw.basicjava.cache.store.DataStore;
import com.jyw.basicjava.cache.store.StoreAccesException;
import com.jyw.basicjava.cache.store.ValueHolder;

import java.util.concurrent.ConcurrentHashMap;

public class WeakRefDataStore<K,V> implements DataStore<K,V> {

    ConcurrentHashMap<K,ValueHolder<V>> map = new ConcurrentHashMap<>();

    @Override
    public ValueHolder<V> get(K key) throws StoreAccesException {
        return map.get(key);
    }

    @Override
    public PUTSTATUS put(K key, V value) throws StoreAccesException {
        if(get(key)!=null){
            return PUTSTATUS.UPDATE;
        }
        map.put(key,new WeakRefValueHolder<V>(value));
        return PUTSTATUS.ERROR;
    }

    @Override
    public ValueHolder<V> remove(K key) throws StoreAccesException {
        return map.remove(key);
    }

    @Override
    public void clear() throws StoreAccesException {
        map.clear();
    }
}
