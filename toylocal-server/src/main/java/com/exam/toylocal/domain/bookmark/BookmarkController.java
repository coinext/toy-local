package com.exam.toylocal.domain.bookmark;

import com.exam.toylocal.base.exception.BookmarkNotFoundException;
import com.exam.toylocal.base.exception.UserNotFoundException;
import com.exam.toylocal.base.exception.UserNotMatchException;
import com.exam.toylocal.domain.common.DataResponse;
import com.exam.toylocal.domain.common.FieldSort;
import com.exam.toylocal.domain.common.Pagination;
import com.exam.toylocal.domain.user.User;
import com.exam.toylocal.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            @RequestBody Bookmark bookmark,
            Principal principal) {

        User user = userService.getByEmail(principal.getName())
                .orElseThrow(UserNotFoundException::new);

        bookmark.setUser(user);

        return new DataResponse<>(bookmarkService.add(bookmark), null);
    }

    @GetMapping()
    public DataResponse<List<Bookmark>, Pagination> getsBookmarkList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction,
            @RequestParam(defaultValue = "created") String sort,
            Principal principal) {

        User user = userService.getByEmail(principal.getName())
                .orElseThrow(UserNotFoundException::new);

        return bookmarkService.gets(user, page, size, new FieldSort(sort, direction));
    }

    @DeleteMapping("/{bookmarkId}")
    public DataResponse<Integer, Void> deleteBookmark(
            @PathVariable Long bookmarkId,
            Principal principal) {

        User user = userService.getByEmail(principal.getName())
                .orElseThrow(UserNotFoundException::new);

        Bookmark bookmark = bookmarkService.get(bookmarkId)
                .orElseThrow(BookmarkNotFoundException::new);

        if (!user.equals(bookmark.getUser())) {
            throw new UserNotMatchException();
        }

        try {
            bookmarkService.delete(bookmarkId);
        } catch (Exception e) {
            return new DataResponse<>(0, null);
        }

        return new DataResponse<>(1, null);
    }
}
