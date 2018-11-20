package com.exam.toylocal.domain.book;

import com.exam.toylocal.base.exception.UserNotFoundException;
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
    private BookServiceFactory bookServiceFactory;

    @Autowired
    private UserService userService;

    @Autowired
    private HistoryService historyService;

    @GetMapping("/search")
    public DataResponse<List<Book>, Pagination> search(
            @RequestParam BookVendor vendor,
            @RequestParam String query,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            Principal principal) {

        User user = userService.getByEmail(principal.getName())
                .orElseThrow(UserNotFoundException::new);

        // 검색 히스토리 저장
        // page를 변경 할때마다 저장되므로
        // 히스토리 저장은 프론트에서 비동기로 변경 예정
        if (page == 1) {
            History history = new History();
            history.setUser(user);
            history.setKeyword(query);

            historyService.add(history);
        }

        BookService bookService = bookServiceFactory.getService(vendor);
        return bookService.search(query, page, size);
    }
}
