package com.shuimin.jtiny.example.mixed;

import com.shuimin.jtiny.codec.StaticFileServer;
import com.shuimin.jtiny.core.Dispatcher;
import com.shuimin.jtiny.core.Server;
import com.shuimin.jtiny.core.mw.Action;
import com.shuimin.jtiny.core.mw.router.Router;

import static com.shuimin.base.S.echo;
import static com.shuimin.base.S.time;

public class App {
    public static void main(String[] args) {

        Dispatcher app = new Dispatcher(Router.regex());

        app.get("/index.html", Action.fly(() ->
                echo("!!!!!" + time())
        ));

        Server.basis(Server.BasicServer.jetty).use(
            new Dispatcher(Router.regex())
        ).use(
            new StaticFileServer(
                "C:\\var\\www")
        ).listen(8080);
    }


}