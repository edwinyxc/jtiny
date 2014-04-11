package com.shuimin.jtiny.core;

/**
 * <p>
 * Middleware </p>
 *
 * @author ed
 */
public interface Middleware {

    public ExecutionContext handle(ExecutionContext ctx);

    public Middleware next();

    /**
     * return the next
     */
    public Middleware next(Middleware ware);


    public default ExecutionContext exec(ExecutionContext ctx) {
        ExecutionContext result = this.handle(ctx);
        for (Middleware ware = this.next(); ware != null; ware = ware.next()) {
            result = ware.handle(result);
        }
        return result;
    }


    /**
     * string middlewares together as an sll
     *
     * @param wares
     * @return
     */
    public static Middleware string(Middleware... wares) {
        if (wares.length == 0) {
            return null;
        }
        Middleware recent = wares[0];
        for (int i = 1; i < wares.length; i++) {
            Middleware now = wares[i];
            if (recent != null) {
                recent.next(now);
            }
            recent = now;
        }
        return wares[0];
    }

}
