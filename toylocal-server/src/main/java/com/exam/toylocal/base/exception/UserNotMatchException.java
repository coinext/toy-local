package com.exam.toylocal.base.exception;

/**
 * @author hahms
 * @since 15/11/2018
 */
public class UserNotMatchException extends RuntimeException {
    public UserNotMatchException() {
        super("User not match");
    }
}
