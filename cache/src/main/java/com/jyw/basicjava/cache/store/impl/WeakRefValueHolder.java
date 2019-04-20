package com.jyw.basicjava.cache.store.impl;

import com.jyw.basicjava.cache.store.ValueHolder;

import java.lang.ref.WeakReference;

/**
 * 弱引用valueHolder，当对象的强引用没有的时候，缓存中的该值会被回收
 */
public class WeakRefValueHolder<V> implements ValueHolder<V> {

    private WeakReference<V> weakReference;

    public WeakRefValueHolder(V value) {
        weakReference = new WeakReference<V>(value);
    }

    @Override
    public V value() {
        return this.weakReference.get();
    }
}
