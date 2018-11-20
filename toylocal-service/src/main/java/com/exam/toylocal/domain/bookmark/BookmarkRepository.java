package com.exam.toylocal.domain.bookmark;

import com.exam.toylocal.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author hahms
 * @since 16/11/2018
 */
@Repository
public interface BookmarkRepository {

    Bookmark save(Bookmark bookmark);

    Optional<Bookmark> findById(Long bookmarkId);

    Page<Bookmark> findByUser(User user, Pageable pageable);

    void deleteById(Long bookmarkId);

}
