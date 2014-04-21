package com.shuimin.jtiny.codec.session;

import com.shuimin.base.f.Function;
import com.shuimin.jtiny.core.misc.Makeable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.shuimin.jtiny.core.ExecutionContext.CUR;

/**
 * Created by ed on 2014/4/18.
 */
public class SessionManager implements Makeable<SessionManager>{

    private final static ConcurrentMap<String,Session> sessions = new ConcurrentHashMap<>();

    public static Session get(String sessionId){
       return get(sessionId,true);
    }

    public static Session get(String sessionId, boolean createOnNotFound) {
        Session ret = sessions.get(sessionId);
        if(ret == null && createOnNotFound) {
            ret = supplierFunc.apply();
            sessions.put(sessionId,ret);
        }
        return ret;
    }

    public static void kill(Session session) {
       sessions.remove(session);
    }

    public static void kill(String sessionId) {
        sessions.remove(sessionId);
    }

    private static Function._0<Session> supplierFunc = () -> new DefaultSession();

//    public static

    /**
     * <p>快捷获得session，如果没有使用 SessionInstaller则可能会出现意想不到的情况</p>
     * @return
     */
    public static Session get() {
        return get(CUR().attr(SessionInstaller.JSESSIONID));
    }

    public static SessionInstaller installer() {
        return new SessionInstaller();
    }

}
