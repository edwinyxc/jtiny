package com.shuimin.jtiny.core;

import com.shuimin.base.S;
import com.shuimin.jtiny.codec.view.View;
import com.shuimin.jtiny.core.exception.YException;

/**
 * @author ed
 */
public interface Interrupt {


    public static abstract class Interruption extends YException {
        private static final Object fakeCause = new Object() {
            @Override
            public String toString() {
                return "This is an interrupt with no cause";
            }
        };

        public Interruption() {
            super(fakeCause);
        }

        public Interruption(Object cause) {
            super(cause);
        }

        @Override
        public Throwable fillInStackTrace() {
            return this;
        }
    }

    public static void redirect(String uri) {
        throw new RedirectInterruption(uri);
    }

    public static void render(View view) {
        throw new RenderViewInterruption(view);
    }

    public static void jump() {
        throw new JumpInterruption();
    }

    public static void jump(Object cause) {
        throw new JumpInterruption(cause);
    }

    public static void kill() {
        throw new KillInterruption();
    }

    /**
     * used in a interception chain to jump out the chain
     */
    @SuppressWarnings("serial")
    public static class JumpInterruption extends Interruption {

        public JumpInterruption(Object cause) {
            super(cause);
        }

        public JumpInterruption() {
            super();
        }

        @Override
        public String brief() {
            return "jump out ";
        }

        @Override
        public String detail() {
            return "jump out from " + cause();
        }

    }

    /**
     * used in a interception chain to jump out the chain
     */
    @SuppressWarnings("serial")
    public static class KillInterruption extends Interruption {

        public KillInterruption(Object cause) {
            super(cause);
        }

        public KillInterruption() {
            super();
        }

        @Override
        public String brief() {
            return "kill ";
        }

        @Override
        public String detail() {
            return "kill by " + cause();
        }

    }

    @SuppressWarnings("serial")
    public static class RedirectInterruption extends Interruption {

        final private String _uri;

        public String uri() {
            return _uri;
        }

        public RedirectInterruption(String uri) {
            super();
            _uri = S._notNull(uri);
        }

        @Override
        public String brief() {
            return "redirect to " + _uri;
        }

        @Override
        public String detail() {
            return brief() + " fired by " + cause();
        }
    }

    @SuppressWarnings("serial")
    public static class RenderViewInterruption extends Interruption {

        final private View _view;

        public View view() {
            return _view;
        }

        public RenderViewInterruption(View view) {
            super();
            _view = S._notNull(view);
        }

        @Override
        public String brief() {
            return "render view " + _view;
        }

        @Override
        public String detail() {
            return brief() + " fired by " + cause();
        }
    }

}
