package com.exam.toylocal.oauth.domain.user;

import com.exam.toylocal.domain.user.User;
import com.exam.toylocal.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author hahms
 * @since 13/11/2018
 */
@Service(value = "userDetailsService")
public class LocalUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return LocalUserDetails.builder().user(user).build();
    }
}
