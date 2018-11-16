package com.exam.toylocal.domain.bookmark;

import com.exam.toylocal.base.exception.UserNotFoundException;
import com.exam.toylocal.domain.book.BookVendor;
import com.exam.toylocal.domain.common.DataResponse;
import com.exam.toylocal.domain.common.FieldSort;
import com.exam.toylocal.domain.common.Pagination;
import com.exam.toylocal.domain.user.User;
import com.exam.toylocal.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * @author hahms
 * @since 16/11/2018
 */
@CrossOrigin
@RestController
@RequestMapping("/v1/bookmark")
public class BookmarkController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookmarkService bookmarkService;

    @PostMapping
    public DataResponse<Bookmark, Void> addBookmark(
            @RequestParam BookVendor vendor,
            @RequestParam String isbn,
            Principal principal) {

        User user = userService.getByEmail(principal.getName())
                .orElseThrow(UserNotFoundException::new);

        Bookmark bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setVendor(vendor);
        bookmark.setIsbn(isbn);

        return new DataResponse<>(bookmarkService.add(bookmark), null);
    }

    @GetMapping()
    public DataResponse<List<Bookmark>, Pagination> getsBookmarkList(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction,
            @RequestParam(defaultValue = "created") String sort,
            Principal principal) {

        User user = userService.getByEmail(principal.getName())
                .orElseThrow(UserNotFoundException::new);

        return bookmarkService.gets(user, page, size, new FieldSort(sort, direction));
    }
}
