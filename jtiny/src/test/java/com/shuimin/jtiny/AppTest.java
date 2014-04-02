package com.shuimin.jtiny;

import com.shuimin.base.S;
import com.shuimin.jtiny.core.Executor;
import com.shuimin.jtiny.mw.Action;
import com.shuimin.jtiny.mw.Dispatcher;
import com.shuimin.jtiny.mw.router.Router;

import static com.shuimin.jtiny.Server.BasicServer.jetty;

public class AppTest {
    public static void simple() {
        Server.basis(jetty).use((req, resp) -> {
            S.echo(req);
            resp.writer().print("sddd");
        }).listen(9090);
    }

    public static void _1() {

        final Executor app = new Executor();

        final Dispatcher dispatcher = new Dispatcher(new Router.RegexRouter());
        dispatcher.make(ctx -> {
            ctx.get("/", Action.simple((req, resp) -> {
            }));
        });

        //config begin

        app.use(dispatcher);

        Server.basis(jetty).use(app).listen(9090);
    }

    public static void main(String[] args) {
        _1();
    }

}
