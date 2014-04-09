package com.shuimin.jtiny.codec.model;

//package com.shuimin.jtiny.codec.model;
//
//import com.shuimin.base.S;
//import Server.G;
//import DbOper;
//import JdbcTmpl;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
///**
// *
// * @author ed
// */
//public abstract class Dao<M extends Model> {
//
//    final static Map<Class<? extends Model>, Dao> _daos = new HashMap<>();
//
//    final static Dao of(Class<? extends Model> modelClass) {
//        return _daos.get(modelClass);
//    }
//
//    final M _prototype;
//    final RowMapper rm;
//
//    public Dao(M proto) {
//        _prototype = proto;
//        rm = (rs) -> {
//            Model ret = Model.createFromProto(_prototype);
//            S._for(ret._fields).each((Entry<String, ModelField> entry) -> {
//                String name = entry.getKey();
//                ModelField field = entry.getValue();
//                try {
//                    ret.set(name, rs.getObject(name,
//                            ModelField.innerType(field.type)));
//                } catch (SQLException ex) {
//                    G.debug(ex);
//                }
//            });
//            return ret;
//        };
//    }
//
////    private Connection _getConn() {
////        try {
////            return G.ctx().connectionPool().getConnection();
////        } catch (SQLException ex) {
////            G.logger().info("failed to get connection");
////            G.debug(ex);
////        }
////        return null;
////    }
//
//    private JdbcTmpl _tmpl() {
//        return new JdbcTmpl(new DbOper(_getConn()));
//    }
//
//    /**
//     * find -one by id
//     *
//     * @param id
//     * @return
//     */
//    public M find(String id) {
//        try {
//            //TODO
//            return (M) S._for(_tmpl().query(
//                    "SELECT * FROM " + _prototype._name + " WHERE "
//                    + rm.keyId() + " = ? ",
//                    new String[]{id}, rm)).first();
//        } catch (SQLException ex) {
//            G.debug(ex);
//        }
//        return null;
//    }
//
//    /**
//     * find - all
//     *
//     * @return
//     */
//    public List<M> all() {
//        try {
//            return S.collection.list.<M>arrayList(
//                    S._for(_tmpl().query("SELECT * FROM "
//                                    + _prototype._name, rm))
//                    .<M>map((i) -> ((M) i)).val());
//
//        } catch (SQLException ex) {
//            G.debug(ex);
//        }
//        return null;
//    }
//
//    public List<M> findBySql(String sql) {
//        try {
//            return S.collection.list.<M>arrayList(
//                    S._for(_tmpl().query(sql, rm))
//                    .<M>map((i) -> ((M) i)).val());
//
//        } catch (SQLException ex) {
//            G.debug(ex);
//        }
//        return null;
//    }
//
////	public Dao add(M model) {
////		String sql
////				= _tmpl().update()
////	}
//    public Dao del(M model) {
//        String id = (String) model.get(rm.keyId());
//        if (S.str.isBlank(id)) {
//            G.logger().info("model has no id");
//            return this;
//        }
//        try {
//            _tmpl().update("DELETE FROM " + _prototype._name + " WHERE "
//                    + rm.keyId() + "= '?'", new String[]{id});
//        } catch (SQLException ex) {
//
//            G.debug(ex);
//        }
//        return this;
//    }
//
////	private public Dao update(M model) {
////		_tmpl().update("UPDATE " + _prototype._name
////				+ " SET " + " WHERE "+ rm.keyId()+" = '?'");
////		return this;
////	}
//}
