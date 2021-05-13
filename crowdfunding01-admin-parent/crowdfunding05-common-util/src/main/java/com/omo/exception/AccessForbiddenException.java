package com.omo.exception;

/**
 * @author MoYu
 * @create 2021-05-07 14:52
 *
 * 未登录就访问受保护资源抛出异常
 */
public class AccessForbiddenException extends RuntimeException {

    private static final long serialVersionUID = 5163710483386528792L;

    public AccessForbiddenException() {
        super();
    }

    public AccessForbiddenException(String message) {
        super(message);
    }

    public AccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessForbiddenException(Throwable cause) {
        super(cause);
    }

    protected AccessForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
