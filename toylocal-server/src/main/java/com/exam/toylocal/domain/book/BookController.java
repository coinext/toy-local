package com.exam.toylocal.domain.book;

import com.exam.toylocal.base.exception.UserNotFoundException;
import com.exam.toylocal.domain.book.kakao.KakaoBookService;
import com.exam.toylocal.domain.common.DataResponse;
import com.exam.toylocal.domain.common.Pagination;
import com.exam.toylocal.domain.history.History;
import com.exam.toylocal.domain.history.HistoryService;
import com.exam.toylocal.domain.user.User;
import com.exam.toylocal.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
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

    @Autowired
    private UserService userService;

    @Autowired
    private HistoryService historyService;

    @GetMapping("/search")
    public DataResponse<List<Book>, Pagination> search(
            @RequestParam String query,
            @RequestParam Integer page,
            @RequestParam Integer size,
            Principal principal) {

        User user = userService.getByEmail(principal.getName())
                .orElseThrow(UserNotFoundException::new);

        // 검색 히스토리 저장
        History history = new History();
        history.setUser(user);
        history.setKeyword(query);

        historyService.add(history);

        return bookService.search(query, page, size);
    }
}
