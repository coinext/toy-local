package com.exam.toylocal.domain.user;

import com.exam.toylocal.domain.common.DataResponse;
import com.exam.toylocal.base.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hahms
 * @since 12/11/2018
 */
@CrossOrigin
@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public DataResponse<User, Void> get(@PathVariable Long userId) {
        User user = userService.get(userId).orElseThrow(UserNotFoundException::new);
        return new DataResponse<>(user, null);
    }

    @PostMapping
    public DataResponse<User, Void> register(@RequestBody User user) {
        return new DataResponse<>(userService.regist(user), null);
    }
}
