package com.shuimin.jtiny.core.mw;

import com.shuimin.base.f.Callback;
import com.shuimin.base.f.Function;
import com.shuimin.jtiny.core.AbstractMiddleware;
import com.shuimin.jtiny.core.ExecutionContext;
import com.shuimin.jtiny.core.http.Request;
import com.shuimin.jtiny.core.http.Response;


/**
 * @author ed
 */
public abstract class Action extends AbstractMiddleware {


    public static Action simple(Callback._2<Request, Response> cb) {
        return new Action() {
            @Override
            public ExecutionContext handle(ExecutionContext ctx) {
                cb.apply(ctx.req(), ctx.resp());
                return ctx;
            }

        };
    }

    public static <T> Action consume(Callback<T> cb) {
        return new Action() {

            @Override
            public ExecutionContext handle(ExecutionContext ctx) {
                T val = (T) ctx.last();
                cb.apply(val);
                return ctx;
            }
        };
    }

    public static <R, T> Action process(Function<R, T> converter) {
        return new Action() {

            @Override
            public ExecutionContext handle(ExecutionContext ctx) {
                return ctx.next(converter.apply((T) ctx.last()));
            }

        };
    }

    public static <T> Action supply(Function._0<T> cb) {
        return new Action() {

            @Override
            public ExecutionContext handle(ExecutionContext ctx) {
                return ctx.next(cb.apply());
            }

        };
    }

    public static <T> Action resolve(Function._2<T, Request, Response> cb) {
        return new Action() {

            @Override
            public ExecutionContext handle(ExecutionContext ctx) {
                return ctx.next(cb.apply(ctx.req(), ctx.resp()));
            }

        };
    }

    public static Action raw(Function<ExecutionContext, ExecutionContext> cb) {
        return new Action() {

            @Override
            public ExecutionContext handle(ExecutionContext ctx) {
                return cb.apply(ctx);
            }

        };
    }

    public static <T> Action end(Callback<T> cb) {
        return new Action() {

            @Override
            public ExecutionContext handle(ExecutionContext ctx) {
                cb.apply((T) ctx.last());
                return null;
            }

        };
    }

    public static Action end(Callback._2<Request, Response> cb) {
        return new Action() {

            @Override
            public ExecutionContext handle(ExecutionContext ctx) {
                cb.apply(ctx.req(), ctx.resp());
                return null;
            }

        };
    }

    public static Action fly(Callback._0 cb) {
        return new Action() {

            @Override
            public ExecutionContext handle(ExecutionContext ctx) {
                cb.apply();
                return null;
            }

        };
    }

}
