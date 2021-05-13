package com.omo.exception;

/**
 * @author MoYu
 * @create 2021-05-06 19:45
 */

//处理登录账号或者密码不一致时，抛出的异常
public class LoginFailedException extends RuntimeException{

    private static final long serialVersionUID = 5163710483389028792L;

    public LoginFailedException() {
    }

    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailedException(Throwable cause) {
        super(cause);
    }

    public LoginFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
