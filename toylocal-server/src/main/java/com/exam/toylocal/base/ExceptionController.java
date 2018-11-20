package com.exam.toylocal.base;

import com.exam.toylocal.base.exception.BookmarkNotFoundException;
import com.exam.toylocal.base.exception.UserNotFoundException;
import com.exam.toylocal.base.exception.UserNotMatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

/**
 * @author hahms
 * @since 15/11/2018
 */
@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler({
            NoSuchElementException.class,
            UserNotFoundException.class,
            BookmarkNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return ErrorResponse.of(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class, UserNotMatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRequestParameterException(Exception e) {
        log.error(e.getMessage(), e);
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handleNotSupportedMethod(Exception e) {
        log.error(e.getMessage(), e);
        return ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    }

    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public ErrorResponse handleRequestTimeout() {
        return ErrorResponse.of(HttpStatus.REQUEST_TIMEOUT);
    }

    @ExceptionHandler({Throwable.class, Exception.class})
    public ErrorResponse handleException(Throwable throwable) {
        log.error(throwable.getMessage(), throwable);
        return ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
