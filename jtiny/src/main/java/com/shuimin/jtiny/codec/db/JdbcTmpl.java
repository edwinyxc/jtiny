package com.shuimin.jtiny.codec.db;

/**
 * Created by ed on 2014/4/18.
 */
public class JdbcTmpl {
    RowMapper rm = (rs)->{
        return null;
    };

    public JdbcTmpl(RowMapper rm){
        this.rm = rm;
    }

}
