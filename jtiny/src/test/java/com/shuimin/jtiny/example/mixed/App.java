package com.shuimin.jtiny.example.mixed;

import com.shuimin.jtiny.codec.StaticFileServer;
import com.shuimin.jtiny.codec.session.SessionManager;
import com.shuimin.jtiny.core.Dispatcher;
import com.shuimin.jtiny.core.Server;
import com.shuimin.jtiny.core.mw.Action;
import com.shuimin.jtiny.core.mw.router.Router;

import static com.shuimin.base.S._notNullElse;
import static com.shuimin.base.S.echo;
import static com.shuimin.jtiny.core.Interrupt.render;
import static com.shuimin.jtiny.core.misc.Renderable.text;

public class App {
    public static void main(String[] args) {

        Dispatcher app = new Dispatcher(Router.regex());

        app.get("/index.html", Action.fly(() -> {
                SessionManager.get("admin").set("username","hello");
                render(text("hello"));
            }
        ));
        app.get("/test", Action.fly(() ->
            render(text(_notNullElse((String) SessionManager.get("admin").get("username"),
                "not set")))
        ));
        app.get("/*", new StaticFileServer("C:\\var\\www"));

        Server.basis(Server.BasicServer.jetty).use((req,resp) -> echo("filter"))
            .use(app
            ).listen(8080);
    }


}