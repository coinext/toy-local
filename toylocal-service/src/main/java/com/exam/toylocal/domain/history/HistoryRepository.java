package com.exam.toylocal.domain.history;

import com.exam.toylocal.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author hahms
 * @since 15/11/2018
 */
@Repository
public interface HistoryRepository {

    Optional<History> findById(Long id);

    History save(History history);

    Page<History> findByUser(User user, Pageable pageable);

}
