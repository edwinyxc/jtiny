package com.shuimin.jtiny.codec.session;

/**
 * Created by ed on 2014/4/18.
 */
public interface Session {

    public Session set(String key, Object value);

    public Object get(String key);

}
