package com.exam.toylocal.domain.user;

import com.exam.toylocal.base.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        User user = userService.get(userId).orElseThrow(null);
        return new DataResponse<>(user, null);
    }
}
