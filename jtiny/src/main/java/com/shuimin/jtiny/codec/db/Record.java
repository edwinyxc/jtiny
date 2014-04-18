package com.shuimin.jtiny.codec.db;


import com.shuimin.base.util.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.shuimin.base.S._notNullElse;

/**
 * <p>数据记录抽象</p>
 */
public class Record extends HashMap<String,Object> {

    public static Logger logger = Logger.get();
    public String tableName;

    public final Map<String,Class<?>> cols = new HashMap<>();

    public Record define(String key, Class<?> type){
        cols.put(key, type);
        return this;
    }

    @SuppressWarnings("unchecked")
    public Object get(String key) {
        Class<?> clazz =
            _notNullElse(cols.get(key),Object.class);
        Object o  = super.get(key);
        if(o == null) return null;
        if(clazz.isAssignableFrom(o.getClass())){
            return o ;
        }
        return o;
    }

}
