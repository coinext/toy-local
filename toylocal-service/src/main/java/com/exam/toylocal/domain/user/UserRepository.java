package com.exam.toylocal.domain.user;

import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author hahms
 * @since 12/11/2018
 */
@Repository
public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    User save(User user);

}
