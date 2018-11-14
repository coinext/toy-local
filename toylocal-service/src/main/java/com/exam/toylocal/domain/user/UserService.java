package com.exam.toylocal.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
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
}
