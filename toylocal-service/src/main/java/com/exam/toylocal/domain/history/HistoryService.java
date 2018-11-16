package com.exam.toylocal.domain.history;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hahms
 * @since 15/11/2018
 */
@Service
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    public History add(History history) {
        return historyRepository.save(history);
    }
}
