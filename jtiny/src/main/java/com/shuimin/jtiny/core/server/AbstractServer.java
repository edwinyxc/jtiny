package com.shuimin.jtiny.core.server;

import com.shuimin.jtiny.core.RequestHandler;
import com.shuimin.jtiny.core.Server;
import com.shuimin.jtiny.core.exception.HttpException;
import com.shuimin.jtiny.core.exception.YException;

import java.util.LinkedList;

import static com.shuimin.base.S._for;

/**
 * Created by ed on 2014/4/11.
 */
public abstract class AbstractServer implements Server {
    private final LinkedList<RequestHandler> handlers = new LinkedList<>();
    @Override
    public Server use(RequestHandler handler) {
        handlers.addLast(handler);
        return this;
    }

    protected final RequestHandler chainedHandler = (req,resp)->{
       try{
           _for(handlers).each(h -> h.handle(req,resp) ) ;
       }catch (HttpException e){
           resp.sendError(e.code(),e.getMessage());
       }catch (YException e){
           resp.sendError(500,e.toString());
       }catch (RuntimeException e){
           e.printStackTrace();
       }
    };

}
