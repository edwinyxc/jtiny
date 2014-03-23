package com.shuimin.jtiny.core;

import com.shuimin.jtiny.core.http.Request;
import java.util.Locale;

/**
 *
 * @author ed
 */
public interface LocaleResolver {

	 Locale resolve(Request req);
	 
}
