package com.shuimin.jtiny;

import com.shuimin.base.S;
import com.shuimin.jtiny.core.Executor;
import com.shuimin.jtiny.core.Server;
import com.shuimin.jtiny.codec.View;
import com.shuimin.jtiny.core.mw.Action;
import com.shuimin.jtiny.core.mw.Dispatcher;
import com.shuimin.jtiny.core.mw.router.Router;

import static com.shuimin.jtiny.core.Interrupt.render;
import static com.shuimin.jtiny.core.Server.BasicServer.jetty;

public class CoreTest {

    public static void simple() {
        Server.basis(jetty).use((req, resp) -> {
            S.echo(req);
            resp.writer().print("sddd");
        }).listen(9090);

        Server.basis(jetty).use((req, resp) -> S.echo(req)).listen(8080);
    }

    public static void _1() {

        final Executor app = new Executor();

        final Dispatcher dispatcher = new Dispatcher(new Router.RegexRouter());

        dispatcher.make(ctx -> {
            ctx.get("/", Action.simple((req, resp) -> {
                //jump();
                render(View.Text.one().text("Hello"));
            }), Action.simple((req, resp) -> render(View.Text.one().text("ejumped"))));

            ctx.get("/test", Action.supply(() -> ("TEST HELLO")),
                Action.<String>consume((hello) -> render(View.Text.one().text(hello))));
        });


        dispatcher.make(ctx -> {
            ctx.get("/${id}/${user}", Action.simple((req, resp) ->
                    render(View.Text.one().text(
                        "<p>id=" + req.param("id") + "</p>" +
                            "<p>user=" + req.param("user") + "</p>"
                    ))
            ));
        });


        //config begin

        app.use(dispatcher);

        Server.global().mode(Server.RunningMode.debug);

        Server.basis(jetty)
            .use(app).listen(9090);
    }

    public static void main(String[] args) {
        _1();
    }

}
