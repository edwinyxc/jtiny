package com.shuimin.jtiny;

/**
 * Created by ed on 2014/4/2.
 */
public interface Interruptable {

    public default Interrupt interrupt(){
        return Interrupt.on(this);
    }

}
