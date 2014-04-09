package com.shuimin.jtiny.core;

import com.shuimin.base.S;
import com.shuimin.jtiny.core.Server.G;
import com.shuimin.jtiny.core.exception.HttpException;
import com.shuimin.jtiny.core.exception.YException;
import com.shuimin.jtiny.core.http.Request;
import com.shuimin.jtiny.core.http.Response;

import java.util.*;

/**
 * @author ed
 */
public class Executor implements Y {

    private final Map<String, Object> attrs = new HashMap<>();//component holder
    private Middleware head;
    private List<Middleware> mwList = new LinkedList<>();

    @Override
    public Y attr(String name, Object o) {
        attrs.put(name, o);
        return this;
    }

    @Override
    public Y use(Middleware ware) {
        mwList.add(ware);
        return this;
    }

    @Override
    public Object attr(String name) {
        return attrs.get(name);
    }

    @Override
    public void handle(Request req, Response resp) {
        try {
            head = Middleware.string(S.array.of(mwList));
            if (head == null) {
                G.logger().info("nothing to do");
                return;
            }
            head.exec(ExecutionContext.init(req, resp));

        } catch (Interrupt.JumpInterruption jump) {
            return;
        } catch (HttpException e) {
            resp.sendError(e.code(), e.toString());
        } catch (YException e) {
            resp.sendError(500, e.toString());
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();//Never happen!
        }
    }

}
