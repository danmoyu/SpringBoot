package com.omo.exception;

/**
 * @author MoYu
 * @create 2021-05-08 15:48
 *
 * 保存或者更新时，admin账号不唯一时，抛出该异常
 */
public class LoginAcctAlreadyInUserException extends RuntimeException {

    private static final float serialVersionUID = 5163710483386528332L;

    public LoginAcctAlreadyInUserException() {
        super();
    }

    public LoginAcctAlreadyInUserException(String message) {
        super(message);
    }

    public LoginAcctAlreadyInUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInUserException(Throwable cause) {
        super(cause);
    }

    protected LoginAcctAlreadyInUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
