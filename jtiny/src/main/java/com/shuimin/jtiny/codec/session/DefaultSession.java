package com.shuimin.jtiny.codec.session;

import java.util.HashMap;

/**
 * Created by ed on 2014/4/18.
 */
public class DefaultSession extends HashMap<String,Object> implements
    Session{

    @Override
    public Session set(String key, Object value) {
        put(key, value);
        return this;
    }

    @Override
    public Object get(String key) {
        return super.get(key);
    }
}
