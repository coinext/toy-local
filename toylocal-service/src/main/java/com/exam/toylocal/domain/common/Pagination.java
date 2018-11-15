package com.exam.toylocal.domain.common;

import lombok.Builder;
import lombok.Data;

/**
 * @author hahms
 * @since 15/11/2018
 */
@Data
@Builder
public class Pagination {
    private Integer totalCount;
    private Integer pageCount;
    private Boolean isNext;
}
