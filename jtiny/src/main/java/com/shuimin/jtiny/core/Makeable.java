package com.shuimin.jtiny.core;

/**
 * @author ed
 */
@SuppressWarnings("unchecked")
public interface Makeable<V> {

    public default Makeable make(Config<V> y) {
        y.config((V) this);
        return this;
    }
}
