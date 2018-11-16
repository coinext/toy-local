package com.exam.toylocal.domain.history;

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
}
