package com.shuimin.jtiny.codec.db;

import com.shuimin.base.S;
import com.shuimin.base.f.Callback;
import com.shuimin.base.f.Function;
import com.shuimin.base.util.logger.Logger;
import com.shuimin.jtiny.codec.connpool.ConnectionPool;
import com.shuimin.jtiny.core.exception.UnexpectedException;
import com.shuimin.jtiny.core.misc.Makeable;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ed on 2014/4/15.
 * <p>数据库抽象，使用自定义名称（逻辑名称）来区分，
 * 每一个db之间可以是同一个物理实现（例如在mysql中，就是同一个 database）
 * ，也可以是多个。具体采用连接池还是单个连接（on the fly）取决于构造，
 * 单个DB对象持有一个打开的connection，显示调用的关闭不一定会真的释放connection</p>
 */
public class DB implements Makeable<DB>,Closeable {
    private static final Logger logger = Logger.get();
    private JdbcOperator oper;

    public DB() {
    }


    public static <R, M> R fire(Connection connection,
                                         Function<M,JdbcOperator> process,
                                         Function<R, M> finisher) {

        try( DB b = new DB().open(() -> connection)){
            return b.exec(process,finisher);
        }

    }

    public DB open(String driverClass,
                   String connUrl,
                   String username,
                   String password){

        oper = new JdbcOperator(
            newConnection(driverClass,connUrl,username,password));
        return this;
    }

    public JdbcOperator oper(){
        return oper;
    }


    public <T> T query(Function<ResultSet,JdbcOperator> queryFunc,
                       Function<T,ResultSet> mapper) {
        return mapper.apply(queryFunc.apply(oper));
    }

    public <T> void exec(Function<T,JdbcOperator> execFunc,
                         Callback<T> cb){
        cb.apply(execFunc.apply(oper));
    }

    public <R,T> R exec(Function<T, JdbcOperator> process,
                        Function<R, T> finisher) {
        return finisher.apply(process.apply(oper));
    }

    public DB open(Function._0<Connection> connectionSupplier){
        oper = new JdbcOperator(connectionSupplier.apply());
        return this;
    }

    public DB open(ConnectionPool p){
        try {
            oper = new JdbcOperator(p.getConnection());
        } catch (SQLException e) {
            throw new UnexpectedException(e);
        }
        return this;
    }

    protected static Connection newConnection(String driverClass,
                                              String connUrl,
                                              String username,
                                              String password){
        try {
            DriverManager.registerDriver((java.sql.Driver)
                Class.forName(driverClass).newInstance());
        } catch (ClassNotFoundException e) {
            logger.debug("cont find class ["+driverClass+"]");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            return DriverManager.getConnection(connUrl,username,password);
        } catch (SQLException e) {
            logger.debug("cont open connection, sql error: "+e.toString());
            throw new UnexpectedException(e);
        }
    }


    @Override
    public void close()
    {
        if(oper != null){
            oper.close();
        }
    }


    public static String tableName(Class<?> clazz){
        return  S.str.underscore(clazz.getSimpleName());
    }
}
