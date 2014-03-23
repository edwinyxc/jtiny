package com.shuimin.jtiny.core.model;

import com.shuimin.jtiny.core.YConfig;
import java.sql.ResultSet;

/**
 *
 * @author ed
 */
public interface RowMapper {

	default public String keyId() {
		return YConfig.MODEL.DEFAULT_KEY_ID;
	}

	public Model map(ResultSet rs);
}
