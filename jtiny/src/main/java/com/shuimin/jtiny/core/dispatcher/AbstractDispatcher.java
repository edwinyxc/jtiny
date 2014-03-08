package com.shuimin.jtiny.core.dispatcher;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shuimin.base.S;
import com.shuimin.jtiny.Y;
import com.shuimin.jtiny.core.Dispatcher;
import com.shuimin.jtiny.core.HttpException;

public abstract class AbstractDispatcher implements Dispatcher{

	@Override
	public void dispatch(HttpServletRequest req, HttpServletResponse resp) {
		try{
			doDispatch(req, resp);
		}
		catch(HttpException e){
			try {
				resp.sendError(e.code(), e.getMessage());
			} catch (IOException ioe) {
				S._lazyThrow(ioe);
			}
		}
		catch(RuntimeException e){
			try {
				resp.sendError(500,Y.debug()?e.getMessage():"");
			} catch (IOException ioe) {
				S._lazyThrow(ioe);
			}
		}
	}
	
	protected abstract void doDispatch(HttpServletRequest req, HttpServletResponse resp) ;

}
