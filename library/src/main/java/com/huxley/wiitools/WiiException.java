package com.huxley.wiitools;

/**
 * Created by huxley on 2017/4/19.
 */

public class WiiException extends RuntimeException {

    private String code;
    private String message;

    public WiiException() {
        super();
    }

    public WiiException(Throwable cause) {
        super(cause);
    }

    public WiiException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
