package com.shuimin.jtiny.core.dispatcher;

import com.shuimin.base.f.Function;
import com.shuimin.jtiny.Y;
import com.shuimin.jtiny.core.Action;
import com.shuimin.jtiny.core.Dispatcher;
import com.shuimin.jtiny.core.HttpException;
import com.shuimin.jtiny.core.Router;
import com.shuimin.jtiny.core.YException;
import com.shuimin.jtiny.core.aop.ActionExecution;
import com.shuimin.jtiny.core.http.Request;
import com.shuimin.jtiny.core.http.Response;

public abstract class AbstractDispatcher implements Dispatcher {

    @Override
    public void dispatch(Request req, Response resp) {
        try {
            Y.debug("dispatch request " + req);
            Action action = router().route(pathDecoder().apply(req.path()));
            ActionExecution execution = new ActionExecution(
                action, action.interceptors(), req, resp);
            //TODO multi-thread or event-driven
            execution.run();
        } catch (HttpException he) {
            resp.sendError(he.code(), he.getMessage());
        } catch (YException y) {
            Y.debug(y);
            y.printStackTrace();
        }
    }

    protected abstract Function<String, String> pathDecoder();

    protected abstract Router router();

}
