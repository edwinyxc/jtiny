package com.shuimin.jtiny.mw;

import com.shuimin.jtiny.ExecutionContext;
import com.shuimin.jtiny.Middleware;
import com.shuimin.jtiny.mw.router.PathMather;
import com.shuimin.jtiny.mw.router.RegexPathMatcher;


/**
 * Created by ed on 2014/4/2.
 */
public interface  RouteNode extends Middleware{
    public PathMather mather();

    public static RouteNode regexRouteNode(String path, Middleware ware){
        return new RouteNode(){

            @Override
            public ExecutionContext handle(ExecutionContext ctx) {
                return ware.handle(ctx);
            }

            @Override
            public Middleware next() {
                return ware.next();
            }

            @Override
            public Middleware next(Middleware ware) {
                return ware.next(ware);
            }

            @Override
            public ExecutionContext exec(ExecutionContext ctx) throws Throwable {
                return ware.exec(ctx);
            }

            @Override
            public PathMather mather() {
                return new RegexPathMatcher(path);
            }
        };
    }

}
