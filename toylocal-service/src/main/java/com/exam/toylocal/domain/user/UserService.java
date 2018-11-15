package com.exam.toylocal.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author hahms
 * @since 12/11/2018
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> get(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getByEmail(String userName) {
        return userRepository.findByEmail(userName);
    }

    public User regist(User user) {
        return userRepository.save(encryptUser(user));
    }

    private User encryptUser(final User user) {
        if (user.getPassword() != null) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }
        return user;
    }
}
