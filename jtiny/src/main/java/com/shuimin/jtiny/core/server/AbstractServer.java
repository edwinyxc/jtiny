package com.shuimin.jtiny.core.server;

import com.shuimin.jtiny.core.Interrupt;
import com.shuimin.jtiny.core.RequestHandler;
import com.shuimin.jtiny.core.Server;
import com.shuimin.jtiny.core.exception.HttpException;
import com.shuimin.jtiny.core.exception.YException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ed on 2014/4/11.
 */
public abstract class AbstractServer implements Server {
    private final List<RequestHandler> handlers = new LinkedList<>();
    @Override
    public Server use(RequestHandler handler) {
        handlers.add(handler);
        return this;
    }

    protected final RequestHandler chainedHandler = (req,resp)->{
       try{
//           _for(handlers).each(h -> h.handle(req,resp) ) ;
           for(int i = 0; i < handlers.size(); i++){
               try{
                   handlers.get(i).handle(req,resp);
               }
               catch(Interrupt.JumpInterruption jump){
                   //do nothing
                   continue;
               }catch (Interrupt.KillInterruption kill) {
                   break;
               }
               catch (HttpException e){
                   if(e.code() == 404 && i != handlers.size()-1 ){

                   }else {
                       throw e;
                   }
               }
           }
       }
        catch (HttpException e){
           resp.sendError(e.code(),e.getMessage());
       }catch (YException e){
           resp.sendError(500,e.toString());
       }catch (RuntimeException e){
           e.printStackTrace();
       }catch (Throwable th) {
           th.printStackTrace();
       }
    };

}
