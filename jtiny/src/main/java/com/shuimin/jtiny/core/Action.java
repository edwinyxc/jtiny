package com.shuimin.jtiny.core;

import java.util.LinkedList;
import java.util.List;

import com.shuimin.jtiny.core.aop.Interceptor;
import com.shuimin.jtiny.core.aop.Interrupt;
import com.shuimin.jtiny.core.http.Request;
import com.shuimin.jtiny.core.http.Response;
import java.util.Arrays;

public abstract class Action {

    protected String _name;

    protected int _method = HttpMethod.GET;

    public Action on(int method) {
        _method = method;
        return this;
    }

    public boolean allow(String method) {
        return HttpMethod.allow(_method, HttpMethod.fromString(method));
    }

    protected Action() {
        _name = "unknown_action";
    }

    protected Action(String name) {
        _name = name;
    }

    public String name() {
        return _name;
    }

    public Action name(String name) {
        _name = name;
        return this;
    }

    final private List<Interceptor> _interceptors = new LinkedList<>();

    public List<Interceptor> interceptors() {
        return _interceptors;
    }

    final public Action onProcess(Interceptor... cb) {
        _interceptors.addAll(Arrays.asList(cb));
        return this;
    }

    public final void handle(Request req, Response resp) {
        try {
            exec(req, resp);
        } catch (Interrupt.RedirectInterruption d) {
            resp.redirect(d.uri());
        } catch (Interrupt.RenderViewInterruption d) {
            d.view().render(resp);
        }
    }

    protected abstract void exec(Request req, Response resp);

    @Override
    public String toString() {
        return "Action[" + _name + "]";
    }
}
