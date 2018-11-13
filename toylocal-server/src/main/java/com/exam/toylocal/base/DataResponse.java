package com.exam.toylocal.base;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * @author hahms
 * @since 12/11/2018
 */
@Getter
@NoArgsConstructor
public class DataResponse<T> {
    private T data;
    private Long timestamp;

    public DataResponse(T data) {
        this.data = data;
        this.timestamp = ZonedDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli();
    }
}
