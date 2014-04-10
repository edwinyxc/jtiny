package com.shuimin.jtiny.core;

import com.shuimin.jtiny.core.http.Response;
import com.shuimin.jtiny.core.http.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ed
 */
public interface ExecutionContext {

    public Request req();

    public Map<String, Object> attrs();

    public Response resp();

    /**
     * last result holder
     *
     * @return return value of last execution
     */
    public Object last();

    //TODO :加入type-safe

    static ExecutionContext init(ExecutionContextProvider provider) {
        return provider.provide();
    }

    static ExecutionContext init(Request req, Response resp) {
        return new ExecutionContext() {

            @Override
            public Request req() {
                return req;
            }

            @Override
            public Map<String, Object> attrs() {
                return new HashMap<>();
            }

            @Override
            public Response resp() {
                return resp;
            }

            @Override
            public Object last() {
                return null;
            }
        };
    }

    public default ExecutionContext next(Object value) {
        ExecutionContext _this = this;
        return new ExecutionContext() {

            @Override
            public Request req() {
                return _this.req();
            }

            @Override
            public Response resp() {
                return _this.resp();
            }

            @Override
            public Object last() {
                return value;
            }

            @Override
            public Map<String, Object> attrs() {
                return _this.attrs();
            }
        };
    }

}
