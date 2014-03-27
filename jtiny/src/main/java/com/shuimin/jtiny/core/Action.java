package com.shuimin.jtiny.core;

import java.util.LinkedList;
import java.util.List;

import com.shuimin.jtiny.core.aop.Interceptor;
import com.shuimin.jtiny.core.aop.Interrupt;
import com.shuimin.jtiny.core.http.Request;
import com.shuimin.jtiny.core.http.Response;
import java.util.Arrays;

public abstract class Action {
    
    protected String _path = "";
    protected String _name = "unkonwn";
    
    public String name(){
        return _name;
    }
    
    public Action name(String name){
        _name = name;
        return this;
    }
    
    public String path(){
        return _path;
    }
    
    public Action path(String path){
        _name = path;
        return this;
    }

    protected int _method = HttpMethod.GET;

    public int method() {
        return _method;
    }

    protected Action() {
    }

    protected Action(String name) {
        _name = name;
    }

    protected Action(String name, int method) {
        _name = name;
        _method = method;
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

    /**
     * offer a basic logic, override is allowed
     *
     * @param req
     * @param resp
     */
    protected void exec(Request req, Response resp) {
    }

    @Override
    public String toString() {
        return "Action[" + _name + "]";
    }
}
