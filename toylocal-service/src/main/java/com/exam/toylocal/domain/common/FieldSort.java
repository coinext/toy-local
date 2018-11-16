package com.exam.toylocal.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Sort;

/**
 * @author hahms
 * @since 16/11/2018
 */
@Data
@AllArgsConstructor
public class FieldSort {
    private String field;
    private Sort.Direction direction;

}
