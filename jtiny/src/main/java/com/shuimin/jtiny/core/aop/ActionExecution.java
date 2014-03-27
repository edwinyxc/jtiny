package com.shuimin.jtiny.core.aop;

import com.shuimin.base.S;
import com.shuimin.jtiny.Y;
import com.shuimin.jtiny.core.Action;
import com.shuimin.jtiny.core.HttpException;
import com.shuimin.jtiny.core.HttpMethod;
import com.shuimin.jtiny.core.http.Request;
import com.shuimin.jtiny.core.http.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shuimin.jtiny.core.HttpMethod.*;

/**
 *
 * @author ed
 */
public class ActionExecution
    implements Runnable {

    final List<Interceptor> interceptors;
    final Action action;
    final Request req;
    final Response resp;
    private final static ThreadLocal<Map<String, Object>> env
        = new ThreadLocal<>();

    public Object attr(String str) {
        return env.get().get(str);
    }

    public ActionExecution attr(String str, Object o) {
        env.get().put(str, o);
        return this;
    }

    public ActionExecution(
        Action action,
        Iterable<Interceptor> interceptors,
        Request req,
        Response resp
    ) {
        this.interceptors = S.list.one(interceptors);
        this.action = action;
        this.req = req;
        this.resp = resp;
    }

    @Override
    public void run() {
        try {
            env.set(new HashMap<String, Object>());
            //all in one thread;
            ActionExecution act = this;
            for (Interceptor inter : interceptors) {
                Y.debug("intercept at " + inter);
                act = inter.intercept(act);
                if (act == null) {
                    break;
                }
            }
            Y.debug("#entering handle");
            if (allow(action.method(), HttpMethod.of(req.method()))) {
                action.handle(req, resp);
            } else {
                throw new HttpException(406, "wrong method");
            }
        } catch (Interrupt.JumpInterruption jump) {
            Y.debug(jump);
            action.handle(req, resp);
        } catch (Interrupt.RedirectInterruption redirect) {
            redirect.printStackTrace();
            Y.debug(redirect);
            resp.redirect(redirect.uri());
        } catch (Interrupt.RenderViewInterruption render) {
            Y.debug(render);
            render.view().render(resp);
        } finally {
            env.remove();//clean
        }
    }

}
