package com.shuimin.jtiny.core;

import com.shuimin.jtiny.core.http.Response;
import com.shuimin.jtiny.core.server.jetty.HSRequestWrapper;
import com.shuimin.jtiny.core.http.Request;
import com.shuimin.jtiny.core.server.jetty.HSResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ed
 */
public interface ExecutionContextProvider {

    ExecutionContext provide();

    public static ExecutionContextProvider jee(
        final HttpServletRequest req, final HttpServletResponse resp) {
        return () -> (new ExecutionContext() {

            @Override
            public Request req() {
                return new HSRequestWrapper(req);
            }

            @Override
            public Response resp() {
                return new HSResponseWrapper(resp);
            }

            @Override
            public Object last() {
                return null;
            }

            @Override
            public Map<String, Object> attrs() {
                return new HashMap<String, Object>();
            }
        });

    }
}
