package com.shuimin.jtiny.core;


/**
 * @author ed
 */
public interface MiddlewareContext extends RequestHandler {

    default public MiddlewareContext config(Config c) {
        c.config(this);
        return this;
    }

    //    /**
//     * <p>
//     * JEE compatibility </p>
//     *
//     * @param request
//     * @param resp
//     */
//    //TOOD not now
//    public void handle(HttpServletRequest request, HttpServletResponse resp);
//    public App route(String regex, Middleware m);
//    public App use(Middleware m);
    public MiddlewareContext attr(String name, Object o);

    public MiddlewareContext use(Middleware ware);

    public Object attr(String name);

//    /**
//     * bind server & dispatcher to some port
//     *
//     * @param port local port
//     */
//    public void listen(int port);
}
