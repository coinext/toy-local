package com.exam.toylocal.domain.bookmark;

import com.exam.toylocal.domain.common.DataResponse;
import com.exam.toylocal.domain.common.FieldSort;
import com.exam.toylocal.domain.common.Pagination;
import com.exam.toylocal.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author hahms
 * @since 16/11/2018
 */
@Service
public class BookmarkService {
    @Autowired
    private BookmarkRepository bookmarkRepository;

    public Bookmark add(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    public Optional<Bookmark> get(Long bookmarkId) {
        return bookmarkRepository.findById(bookmarkId);
    }

    public DataResponse<List<Bookmark>, Pagination> gets(User user, Integer page, Integer size, FieldSort sort) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, sort.getDirection(), sort.getField());
        Page<Bookmark> bookmarkPage = bookmarkRepository.findByUser(user, pageRequest);

        Pagination pagination = Pagination.builder()
                .totalCount(bookmarkPage.getTotalElements())
                .pageCount(bookmarkPage.getNumberOfElements())
                .isNext(bookmarkPage.getTotalPages() - 1 > bookmarkPage.getNumber())
                .build();

        return new DataResponse<>(bookmarkPage.getContent(), pagination);
    }

    public void delete(Long bookmarkId) {
        bookmarkRepository.deleteById(bookmarkId);
    }
}
