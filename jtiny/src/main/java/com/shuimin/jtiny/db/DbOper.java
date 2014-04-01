package com.shuimin.jtiny.db;

import com.shuimin.base.S;
import com.shuimin.base.util.logger.Logger;
import com.shuimin.jtiny.Server.G;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <a DAO base Class>
 * encapsoluate some basic method to operate database connection
 *
 *
 * @author edwin
 *
 * <pre>
 *  ?????? ?? conn??????
 *  date:2010-4-2;
 *  modified :2013/7/2 --?? ? connection ?????
 * </pre>
 */
public class DbOper {

    public final Connection conn;
    public PreparedStatement pstmt = null;
    public ResultSet rs = null;
    Logger logger = G.logger();

    final DbOper outer = this;
//	//use under jdk -1.4
//	@SuppressWarnings("unused")
//	private Object guarder = new Object() {
//		public void finalize() throws Throwable
//		{
//			outer.disposeAll();
//		}
//	};

    public DbOper(Connection conn) {
        this.conn = conn;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * release resource
     */
    public void disposeAll() {
        _closeRs();
        _closeStmt();
        _closeConn();

    }

    private void _closeStmt() {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                _debug("sql except when closing stmt");
            }
        }
    }

    private void _closeConn() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                _debug("sql except when closing conn");
            }
        }
    }

    private void _closeRs() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                _debug("sql except when closing rs");
            }
        }
    }

    /**
     *
     * @param sql
     * @param params ?????? inputstream ?????blob
     * @return
     */
    public int executeUpdate(String sql, Object[] params)
            throws SQLException {
        if (pstmt != null) {
            _closeStmt();
        }

        pstmt = conn.prepareStatement(sql);

        Object o;
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                o = params[i];
                if (o instanceof String) {
                    pstmt.setString(i + 1, (String) o);
                } else if (o instanceof InputStream) {
                    try {
                        pstmt.setBinaryStream(i + 1,
                                (InputStream) o, ((InputStream) o).available());
                    } catch (IOException ex) {
                        S._lazyThrow(ex);
                    }
                }
            }
        }
        _debug(pstmt);

        if (rs != null) {
            _closeRs();
        }
        return pstmt.executeUpdate();
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        return executeQuery(sql, null);
    }

    /**
     * execute SQL statements to Query
     */
    public ResultSet executeQuery(String sql, String[] params) throws SQLException {
        if (pstmt != null) {
            _closeStmt();
        }
        pstmt = conn.prepareStatement(sql);

        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }
        }

        _debug(pstmt);
        if (rs != null) {
            _closeRs();
        }
        rs = pstmt.executeQuery();
        return rs;
    }

    public int executeUpdate(String sql) throws SQLException {
        return executeUpdate(sql, null);
    }

//	/**
//	 * execute SQL statements to Update,Modify or Delete
//	 */
//	public int executeUpdate(String sql, String[] params) throws SQLException {
//		int num = 0;
//		if (pstmt != null) {
//			_closeStmt();
//		}
//		pstmt = conn.prepareStatement(sql);
//
//		if (params != null) {
//			for (int i = 0; i < params.length; i++) {
//				pstmt.setString(i + 1, params[i]);
//			}
//		}
//
//		_debug(pstmt);
//		num = pstmt.executeUpdate();
//		return num;
//	}
    public void transactionStart() throws SQLException {
        synchronized (conn) {
            conn.setAutoCommit(false);
        }
    }

    public void transactionCommit() throws SQLException {
        try {
            synchronized (conn) {
                conn.commit();
            }
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            synchronized (conn) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public void rollback() throws SQLException {
        conn.rollback();
    }

    private void _debug(Object o) {
        if (logger != null) {
            logger.debug(o);
        }
    }

}
