package com.exam.toylocal.domain.book;

import com.exam.toylocal.domain.common.DataResponse;
import com.exam.toylocal.domain.book.kakao.KakaoBookService;
import com.exam.toylocal.domain.common.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hahms
 * @since 14/11/2018
 */
@CrossOrigin
@RestController
@RequestMapping("/v1/book")
public class BookController {
    @Autowired
    private KakaoBookService bookService;

    @GetMapping("/search")
    public DataResponse<List<Book>, Pagination> search(
            @RequestParam String query,
            @RequestParam Integer page,
            @RequestParam Integer size) {
        return bookService.search(query, page, size);
    }
}
