package com.shuimin.jtiny.codec.model;

import java.sql.ResultSet;

/**
 * @author ed
 */
public interface RowMapper {

    default public String keyId() {
        return null;
//		return Config.MODEL.DEFAULT_KEY_ID;
    }

    public Model map(ResultSet rs);
}
