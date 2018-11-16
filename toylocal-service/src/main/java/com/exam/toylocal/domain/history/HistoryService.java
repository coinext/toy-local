package com.exam.toylocal.domain.history;

import com.exam.toylocal.domain.common.DataResponse;
import com.exam.toylocal.domain.common.FieldSort;
import com.exam.toylocal.domain.common.Pagination;
import com.exam.toylocal.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public DataResponse<List<History>, Pagination> gets(User user, Integer page, Integer size, FieldSort sort) {
        PageRequest pageRequest = PageRequest.of(page, size, sort.getDirection(), sort.getField());
        Page<History> historyPage = historyRepository.findByUser(user, pageRequest);

        Pagination pagination = Pagination.builder()
                .totalCount(historyPage.getTotalElements())
                .pageCount(historyPage.getNumberOfElements())
                .isNext(historyPage.getTotalPages() - 1 > historyPage.getNumber())
                .build();

        return new DataResponse<>(historyPage.getContent(), pagination);
    }
}
