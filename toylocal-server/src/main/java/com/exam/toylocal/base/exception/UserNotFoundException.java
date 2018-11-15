package com.exam.toylocal.base.exception;

/**
 * @author hahms
 * @since 15/11/2018
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found");
    }
}
