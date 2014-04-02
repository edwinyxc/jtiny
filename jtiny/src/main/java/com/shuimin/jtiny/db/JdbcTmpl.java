package com.shuimin.jtiny.db;

import com.shuimin.jtiny.model.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTmpl {
    final private DbOper dbOper;

    public JdbcTmpl(DbOper dbOper) {
        this.dbOper = dbOper;
    }

    public List<?> query(String sql, RowMapper rm) throws SQLException {
        return query(sql, null, rm);
    }

    public List<?> query(String sql, String[] params, RowMapper rm)
        throws SQLException {
        ResultSet rs = dbOper.executeQuery(sql, params);
        List<Object> list = new ArrayList<Object>();
        Object po;
        while (rs.next()) {
            po = rm.map(rs);
            list.add(po);
        }
        return list;
    }

    public int count(String sql) throws SQLException {
        ResultSet rs = dbOper.executeQuery(sql, null);
        while (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    public int update(String sql) throws SQLException {
        return update(sql, null);
    }

    public int update(String sql, Object[] params) throws SQLException {
        return dbOper.executeUpdate(sql, params);
    }
}