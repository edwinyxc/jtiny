package com.shuimin.jtiny.core;

import com.shuimin.jtiny.core.exception.HttpException;
import com.shuimin.jtiny.core.http.HttpMethod;
import com.shuimin.jtiny.core.http.Request;
import com.shuimin.jtiny.core.http.Response;
import com.shuimin.jtiny.core.misc.Attrs;
import com.shuimin.jtiny.core.misc.Config;
import com.shuimin.jtiny.core.misc.Makeable;
import com.shuimin.jtiny.core.mw.router.Router;

/**
 * @author ed
 */
public class Dispatcher
    implements Makeable<Dispatcher>,RequestHandler,Attrs<Dispatcher> {

    final private Router router;

    public Dispatcher(Router r) {
        router = r;
    }

    public Dispatcher bind(int mask, String pattern, Middleware... mw) {
        router.add(mask, pattern, mw);
        return this;
    }

    public Dispatcher get(String pattern, Middleware... mw) {
        router.add(HttpMethod.mask(HttpMethod.GET), pattern, mw);
        return this;
    }

    public Dispatcher post(String pattern, Middleware... mw) {
        router.add(HttpMethod.mask(HttpMethod.POST), pattern, mw);
        return this;
    }

    public Dispatcher delete(String pattern, Middleware... mw) {
        router.add(HttpMethod.mask(HttpMethod.DELETE), pattern, mw);
        return this;
    }

    public Dispatcher put(String pattern, Middleware... mw) {
        router.add(HttpMethod.mask(HttpMethod.PUT), pattern, mw);
        return this;
    }


    public Router router() {
        return router;
    }

    public Dispatcher use(Config<Dispatcher> config) {
        config.config(this);
        return this;
    }


    @Override
    public void handle(Request req, Response resp) {
        ExecutionContext ctx = ExecutionContext.init(req, resp);
        try {
            Middleware processor = router.route(ctx);
            if(processor == null) throw new HttpException(404,req.path());
            processor.exec(ctx);
        } catch (Interrupt.JumpInterruption jump) {
            //if jump do nothing but return the same as the input
            return;
        } catch (Interrupt.RedirectInterruption redirection) {
            ctx.resp().redirect(redirection.uri());
        } catch (Interrupt.RenderViewInterruption render) {
            render.view().render(ctx.resp());
        }
    }
}
