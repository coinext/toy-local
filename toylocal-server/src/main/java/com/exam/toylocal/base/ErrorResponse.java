package com.exam.toylocal.base;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * @author hahms
 * @since 15/11/2018
 */
@Getter
public class ErrorResponse {
    private final int status;
    private final String error;
    private final String exception;
    private final String message;
    private final long timestamp;

    private ErrorResponse(int status, String error, String message, String exception) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.exception = exception;
        this.timestamp = ZonedDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli();
    }

    public static ErrorResponse of(HttpStatus status, String message, String exception) {
        return new ErrorResponse(status.value(), status.getReasonPhrase(), message, exception);
    }

    public static ErrorResponse of(HttpStatus status, String message) {
        return new ErrorResponse(status.value(), status.getReasonPhrase(), message, null);
    }

    public static ErrorResponse of(HttpStatus status) {
        return new ErrorResponse(status.value(), status.getReasonPhrase(), null, null);
    }
}
