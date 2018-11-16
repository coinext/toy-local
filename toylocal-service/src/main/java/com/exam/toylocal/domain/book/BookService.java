package com.exam.toylocal.domain.book;

import com.exam.toylocal.domain.common.DataResponse;
import com.exam.toylocal.domain.common.Pagination;

import java.util.List;

/**
 * @author hahms
 * @since 16/11/2018
 */
public interface BookService {
    DataResponse<List<Book>, Pagination> search(String query, Integer page, Integer size);
}
