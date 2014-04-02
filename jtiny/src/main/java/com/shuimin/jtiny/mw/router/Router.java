package com.shuimin.jtiny.mw.router;

import com.shuimin.base.S;
import com.shuimin.jtiny.ExecutionContext;
import com.shuimin.jtiny.Middleware;
import com.shuimin.jtiny.http.HttpMethod;
import com.shuimin.jtiny.mw.RouteNode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author ed
 */
public interface Router {

    Middleware route(ExecutionContext ctx);

    Router add(HttpMethod method,String pattern, Middleware... mw);

    public static class RegexRouter implements Router {

        @Override
        public Middleware route(ExecutionContext ctx) {
            HttpMethod method = HttpMethod.of(ctx.req().method());

            List<RouteNode> routes = Routes.get(method);

            for (RouteNode node : routes) {
                if (node.mather().match(ctx.req())) {
                    return node;
                }
            }
            //not found
            return null;
            //not so hurry to throw an exception
//            throw new HttpException(404, "request " + req.toString() + "not found");
        }

        @Override
        public Router add(HttpMethod method, String path, Middleware... wares) {
            List<RouteNode> routes = Routes.get(method);
            S._assert(routes,"routes of method["+method.toString()+"] not found");
            synchronized (routes) {
                routes.add(RouteNode.regexRouteNode(path, Middleware.string(wares)));
            }
            return this;
        }

        private static class Routes {
            final private static List<RouteNode>[] all = new List[HttpMethod.values().length];

            final static List<RouteNode> get(HttpMethod method) {
                List<RouteNode> ret = all[method.ordinal()];
                if (ret == null) {
                    ret = (all[method.ordinal()] = new LinkedList<>());
                }
                return ret;
            }
        }

    }

    public static Router regex() {
        return new RegexRouter();
    }
    //public static Router regex = new TreeRouter();//TODO
}
