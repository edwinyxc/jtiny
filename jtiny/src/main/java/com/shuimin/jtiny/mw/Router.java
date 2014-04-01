package com.shuimin.jtiny.mw;

import com.shuimin.base.S;
import com.shuimin.jtiny.ExecutionContext;
import com.shuimin.jtiny.Middleware;
import com.shuimin.jtiny.core.UriParser;
import com.shuimin.jtiny.core.exception.HttpException;
import com.shuimin.jtiny.http.Request;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

/**
 *
 * @author ed
 */
public interface Router {

    Middleware route(ExecutionContext ctx);

    Router route(String pattern, Middleware... mw);

    public static class RegexRouter implements Router {

        @Override
        public Middleware route(ExecutionContext ctx) {
            Map<String, List<Middleware>> routes = Routes.routes;
            Request req = ctx.req();
            for (Entry<String, List<Middleware>> entry : routes.entrySet()) {
                String pattern = entry.getKey();
                if (UriParser.regex.matchThenParse(req, Pattern.compile(pattern))) {
                    return entry.getValue();
                }
            }
            throw new HttpException(404, "request " + req.toString() + "not found");
        }

        @Override
        public Router route(String pattern, Middleware... mw) {
            synchronized (Routes.routes) {
                Routes.routes.put(pattern, S.list.one(mw));
            }
            return this;
        }

        private static class Routes {

            final private static Map<String, List<Middleware>> routes = new LinkedHashMap<>();
        }

    }

    public static Router regex() {
        return new RegexRouter();
    }
    //public static Router regex = new TreeRouter();//TODO
}
