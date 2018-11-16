package com.exam.toylocal.infra.jpa;

import com.exam.toylocal.domain.history.History;
import com.exam.toylocal.domain.history.HistoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hahms
 * @since 15/11/2018
 */
public interface HistoryRepositoryImpl extends JpaRepository<History, Long>, HistoryRepository {
}
