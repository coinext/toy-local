package com.exam.toylocal.domain.user;

import java.util.Optional;

/**
 * @author hahms
 * @since 12/11/2018
 */
public interface UserRepository {

    Optional<User> findById(Long id);
}
