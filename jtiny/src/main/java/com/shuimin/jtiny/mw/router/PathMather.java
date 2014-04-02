package com.shuimin.jtiny.mw.router;

import com.shuimin.jtiny.http.Request;

/**
 * Created by ed on 2014/4/2.
 */
public interface PathMather {

    public boolean match(Request req);

}
