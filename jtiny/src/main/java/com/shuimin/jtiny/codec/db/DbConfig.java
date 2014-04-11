package com.shuimin.jtiny.codec.db;

/**
 * Created by ed on 2014/4/11.
 */
public class DbConfig {
    public final String maxPoolSize;
    public final String driverClass;
    public final String username;
    public final String password;
    public final String connectionUrl;

    public DbConfig(String maxPoolSize, String driverClass, String username, String password, String connectionUrl) {
        this.maxPoolSize = maxPoolSize;
        this.driverClass = driverClass;
        this.username = username;
        this.password = password;
        this.connectionUrl = connectionUrl;
    }


}
