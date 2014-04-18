//package com.shuimin.jtiny.example.db;
//
//import com.shuimin.jtiny.codec.connpool.ConnectionConfig;
//import com.shuimin.jtiny.codec.connpool.ConnectionPool;
//import com.shuimin.jtiny.codec.db.DB;
//import com.shuimin.jtiny.core.Dispatcher;
//import com.shuimin.jtiny.core.Server;
//import com.shuimin.jtiny.core.mw.Action;
//import com.shuimin.jtiny.core.mw.router.Router;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
///**
// * Created by ed on 2014/4/17.
// */
//public class DbTest {
//
//
//    public static Server createServer(Dispatcher app){
//        return Server.basis(Server.BasicServer.jetty).use(app);
//    }
//
//    public static ConnectionPool createPool() {
//        ConnectionPool pool = new ConnectionPool();
//        try {
//            pool.init(new ConnectionConfig(
//               "10","com.mysql.jdbc.Driver","root","root",
//                "jdbc:mysql://localhost:3306/test"
//            ));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return pool;
//    }
//
//    public static void main(String[] args) {
//        ConnectionPool pool = createPool();
//        Dispatcher app = new Dispatcher(Router.regex());
//        app.get("/db", Action.fly(()->{
//            try {
//                DB.fire(pool.getConnection(),(jdbc)->{
//                    String sql = "SELECT * FROM tb_test";
//                    ResultSet rs = jdbc.executeQuery(sql);
//                    try {
//                    while (rs.next()){
//
//                    }
//                },);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }));
//        createServer(app).listen(8080);
//    }
//}
