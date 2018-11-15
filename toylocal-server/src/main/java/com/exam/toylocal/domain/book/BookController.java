package com.exam.toylocal.domain.book;

import com.exam.toylocal.base.DataResponse;
import com.exam.toylocal.domain.book.kakao.KakaoBookService;
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
    public DataResponse<List<Book>, Void> search(@RequestParam String query) {
        return new DataResponse<>(bookService.search(query), null);
    }
}
