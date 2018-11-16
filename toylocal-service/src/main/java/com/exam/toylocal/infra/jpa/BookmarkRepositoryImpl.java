package com.exam.toylocal.infra.jpa;

import com.exam.toylocal.domain.bookmark.Bookmark;
import com.exam.toylocal.domain.bookmark.BookmarkRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hahms
 * @since 15/11/2018
 */
public interface BookmarkRepositoryImpl extends JpaRepository<Bookmark, Long>, BookmarkRepository {
}
