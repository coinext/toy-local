package com.exam.toylocal.domain.history;

import com.exam.toylocal.base.exception.UserNotFoundException;
import com.exam.toylocal.domain.common.DataResponse;
import com.exam.toylocal.domain.common.FieldSort;
import com.exam.toylocal.domain.common.Pagination;
import com.exam.toylocal.domain.user.User;
import com.exam.toylocal.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/v1/history")
public class HistoryController {

    @Autowired
    private UserService userService;

    @Autowired
    private HistoryService historyService;

    @GetMapping()
    public DataResponse<List<History>, Pagination> search(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction,
            @RequestParam(defaultValue = "created") String sort,
            Principal principal) {

        User user = userService.getByEmail(principal.getName())
                .orElseThrow(UserNotFoundException::new);

        return historyService.gets(user, page, size, new FieldSort(sort, direction));
    }
}
