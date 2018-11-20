package com.exam.toylocal.base.exception;

/**
 * @author hahms
 * @since 15/11/2018
 */
public class BookmarkNotFoundException extends RuntimeException {
    public BookmarkNotFoundException() {
        super("Bookmark not found");
    }
}
