package com.jyw.basicjava.cache.store;

/**
 * 定义获取存储的对象的值的接口
 * @param <V>
 */
public interface ValueHolder<V> {
    V value();
}
