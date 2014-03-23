package com.shuimin.jtiny;

import com.shuimin.jtiny.core.Action;
import com.shuimin.jtiny.core.HttpMethod;
import com.shuimin.jtiny.core.View;
import com.shuimin.jtiny.core.aop.Interrupt;
import com.shuimin.jtiny.core.http.Request;
import com.shuimin.jtiny.core.http.Response;

public class AppTest {

    public void test() {

    }

    public static void main(String[] args) {
        Y.config().name("test").debug(true).port(8080);
        Y.resources().bind("/", new Action() {

            @Override
            protected void exec(Request req, Response resp) {
                Interrupt.on(this).redirect("http://www.baidu.com");
            }

        });
        Y.resources().bind("/test/base", new Action() {

            @Override
            protected void exec(Request req, Response resp) {
                Interrupt.on(this).render(
                    View.Html.one().text("<h1>Hello World!///sd</h1>"));
            }

        });
        Y.resources().bind("/test/echo", new Action() {

            @Override
            protected void exec(Request req, Response resp) {
                Interrupt.on(this).render(
                    View.Html.one().text("<h1>" + req.toString() + "</h1>"));
            }

        });
        Y.resources().bind("/test/onlypost", new Action() {

            @Override
            protected void exec(Request req, Response resp) {
            }

        }.on(HttpMethod.POST));

        Y.start();
    }

}
