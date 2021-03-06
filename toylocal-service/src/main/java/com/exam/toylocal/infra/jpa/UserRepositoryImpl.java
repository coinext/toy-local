package com.exam.toylocal.infra.jpa;

import com.exam.toylocal.domain.user.User;
import com.exam.toylocal.domain.user.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hahms
 * @since 12/11/2018
 */
public interface UserRepositoryImpl extends JpaRepository<User, Long>, UserRepository {
}
