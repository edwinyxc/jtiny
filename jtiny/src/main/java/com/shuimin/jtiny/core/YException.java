package com.shuimin.jtiny.core;

import com.shuimin.jtiny.Y;

/**
 *
 * @author ed
 */
public abstract class YException extends RuntimeException {

    final Object cause;

    public YException(Object cause) {
        this.cause = cause;
    }

    public String brief() {
        return this.getMessage();
    }

    public String detail() {
        return ": caused by " + cause().toString();
    }

    //show where the Exception has been thrown
    public final Object cause() {
        return cause;
    }

    @Override
    public String toString() {
        return Y.debug() ? detail() : brief();
    }
}
