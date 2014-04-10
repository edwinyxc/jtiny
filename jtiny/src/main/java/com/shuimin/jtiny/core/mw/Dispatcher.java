package com.shuimin.jtiny.core.mw;

import com.shuimin.base.S;
import com.shuimin.jtiny.core.*;
import com.shuimin.jtiny.core.http.HttpMethod;
import com.shuimin.jtiny.core.mw.router.Router;

/**
 * @author ed
 */
public class Dispatcher extends AbstractMiddleware implements Makeable<Dispatcher> {

    final private Router router;

    public Dispatcher(Router r) {
        router = r;
    }

    @Override
    public ExecutionContext handle(ExecutionContext ctx) {
        try {
            Middleware mw = router.route(ctx);
            return mw == null ? ctx : mw.exec(ctx);
        } catch (Interrupt.JumpInterruption jump) {
            //if jump do nothing but return the same as the input
            return ctx;
        } catch (Interrupt.RedirectInterruption redirection) {
            ctx.resp().redirect(redirection.uri());
            return null;
        } catch (Interrupt.RenderViewInterruption render) {
            render.view().render(ctx.resp());
            return null;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            S._lazyThrow(throwable);
            return null;
        }
    }

    public Dispatcher bind(int mask, String pattern, Middleware... mw){
        router.add(mask, pattern,mw);
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

}
