package com.shuimin.jtiny;

import com.shuimin.base.S;

import static com.shuimin.jtiny.Server.BasicServer.jetty;

import com.shuimin.jtiny.core.Executor;
import com.shuimin.jtiny.core.View;
import com.shuimin.jtiny.http.Request;
import com.shuimin.jtiny.http.Response;
import com.shuimin.jtiny.mw.Action;
import com.shuimin.jtiny.mw.Dispatcher;
import com.shuimin.jtiny.mw.Router;

public class AppTest {
    public static void simple() {
        Server.basis(jetty).use((req, resp) -> {
            S.echo(req);
            resp.writer().print("sddd");
        }).listen(9090);
    }

    public static void _1() {


        final Dispatcher app = new Dispatcher(new Router.RegexRouter());
        app.make(ctx -> {
            ctx.route("/", Action.simple((req, resp) -> {
                resp.redirect("http://baidu.com");
            }));
        });
        Server.basis(jetty).use(app).listen(9090);
    }

    public static void main(String[] args) {
        _1();
    }

}
