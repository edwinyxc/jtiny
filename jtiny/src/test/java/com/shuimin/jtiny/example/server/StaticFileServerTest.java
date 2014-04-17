package com.shuimin.jtiny.example.server;

import com.shuimin.jtiny.codec.StaticFileServer;
import com.shuimin.jtiny.core.Server;

/**
 * Created by ed on 2014/4/11.
 */
public class StaticFileServerTest {

    public static void main(String[] args) {
        Server.basis(Server.BasicServer.jetty)
            .use(new StaticFileServer("C:\\var\\www")
                    .defaultPages(()-> new String[]{"index.html","index.js"})
            ).listen(10000);
    }

}
