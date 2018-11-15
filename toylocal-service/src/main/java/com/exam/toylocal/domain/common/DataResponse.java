package com.exam.toylocal.domain.common;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class DataResponse<T, M> {
    private T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private M meta;
    private Long timestamp;

    public DataResponse(T data, M meta) {
        this.data = data;
        this.meta = meta;
        this.timestamp = ZonedDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli();
    }
}
