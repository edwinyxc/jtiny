package com.shuimin.jtiny.core;

import com.shuimin.base.S;
import static com.shuimin.base.S.array.*;
import static com.shuimin.base.S.*;
import java.util.Iterator;

/**
 *
 * @author ed
 */
public class Resource implements Iterable<Action> {

    String name;
    final String path;
    final Action[] actions = new Action[5];

    final static private int GET = 0;
    final static private int POST = 1;
    final static private int DELETE = 2;
    final static private int PUT = 3;
    final static private int HEAD = 4;

    public String name() {
        return name;
    }

    public Resource name(String name) {
        this.name = name;
        return this;
    }

    private static int convert(int httpMethod) {
        switch (httpMethod) {
            case HttpMethod.GET:
                return GET;
            case HttpMethod.POST:
                return POST;
            case HttpMethod.PUT:
                return PUT;
            case HttpMethod.DELETE:
                return DELETE;
            case HttpMethod.HEAD:
                return HEAD;
        }
        return 0;
    }

    public Resource(String path) {
        this.path = path;
        name = _notNullElse(last(path.split("/")), "");
    }

    public Resource action(Action a) {
        int method = a._method;
        if (HttpMethod.allow(HttpMethod.GET, method)) {
            actions[GET] = a;
        }
        if (HttpMethod.allow(HttpMethod.POST, method)) {
            actions[POST] = a;
        }
        if (HttpMethod.allow(HttpMethod.PUT, method)) {
            actions[PUT] = a;
        }
        if (HttpMethod.allow(HttpMethod.DELETE, method)) {
            actions[DELETE] = a;
        }
        if (HttpMethod.allow(HttpMethod.HEAD, method)) {
            actions[HEAD] = a;
        }
        return this;
    }

    public Action action(int method) {
        return actions[convert(method)];
    }

    @Override
    public Iterator<Action> iterator() {
        return S._for(actions).compact().val().iterator();
    }

}
