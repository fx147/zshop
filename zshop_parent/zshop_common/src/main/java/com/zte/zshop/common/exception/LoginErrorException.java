package com.zte.zshop.common.exception;

/**
 * Author:helloboy
 * Date:2022-05-13 11:35
 * Description:<描述>
 */
public class LoginErrorException extends Exception {

    public LoginErrorException() {
    }

    public LoginErrorException(String message) {
        super(message);
    }

    public LoginErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginErrorException(Throwable cause) {
        super(cause);
    }

    public LoginErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
