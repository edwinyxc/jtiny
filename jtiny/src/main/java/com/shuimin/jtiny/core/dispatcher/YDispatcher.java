package com.shuimin.jtiny.core.dispatcher;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.shuimin.base.S;
import com.shuimin.base.f.Function;
import com.shuimin.jtiny.Y;
import com.shuimin.jtiny.core.Action;
import com.shuimin.jtiny.core.HttpException;
import com.shuimin.jtiny.core.LocaleResolver;
import com.shuimin.jtiny.core.Router;
import com.shuimin.jtiny.core.YConfig;
import java.util.Locale;
import javax.servlet.http.Cookie;

public class YDispatcher extends AbstractDispatcher {

    LocaleResolver _localeResolver;
    Router _router;

    {
        _router = (path) -> {
            Y.debug(path);
            Action a = Y.resources().get(path);
            if (a == null) {
                throw new HttpException(404, "[" + path + " ]not found");
            }
            Y.debug("find action " + a.toString());
            return a;
        };
    }

    {
        _localeResolver = (req) -> {
            Locale ret = new Locale("en", "US");//default
            //first query cookie;
            Cookie c = req.cookie(YConfig.COOKIE_LOCALE);
            if (c != null) {
                String lc_str = c.getValue();
                String[] lc_str_parsed = lc_str.split("_");
                if (lc_str_parsed == null || lc_str_parsed.length < 2) {
                    Y.logger().info(
                        "bad format for cookie["
                        + YConfig.COOKIE_LOCALE + "] ");
                }
                return new Locale(lc_str_parsed[0], lc_str_parsed[1]);
            }
            //else in  header;
//			String head_lc = req.header("accept-language");
//			String[] head_lc_parsed = head_lc.split(",");
//			if (head_lc_parsed.length > 1) {
//				String[] real_lc = head_lc_parsed[0].split(",");
//				return new Locale(real_lc[0], real_lc[1]);
//			}
            return req.locale();
        };
    }

    @Override
    public void localeResolver(LocaleResolver lr) {
        _localeResolver = lr;
    }

    @Override
    protected Router router() {
        return _router;
    }

    @Override
    protected Function<String, String> pathDecoder() {
        return (path) -> {
            try {
                return URLDecoder.decode(path, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                S._lazyThrow(ex);
            }
            return null;
        };
    }
}
