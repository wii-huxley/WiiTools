package com.huxley.wiitools;

/**
 * Created by huxley on 2017/4/19.
 */

public class WiiException extends RuntimeException {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -6213149635297151442L;

    public WiiException(String message) {
        super(message);
    }

    public WiiException(String message, Throwable cause) {
        super(message, cause);
    }

    public WiiException() {
        super();
    }

    public WiiException(Throwable cause) {
        super(cause);
    }
}
