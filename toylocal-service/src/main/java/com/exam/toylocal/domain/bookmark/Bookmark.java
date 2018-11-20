package com.exam.toylocal.domain.bookmark;

import com.exam.toylocal.domain.book.BookVendor;
import com.exam.toylocal.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.ZonedDateTime;

/**
 * @author hahms
 * @since 16/11/2018
 */
@Data
@Entity
@Table
@NoArgsConstructor
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @JsonIgnore
    @ManyToOne
    User user;

    String title;

    @Lob
    String url;

    String isbn;

    BookVendor vendor;

    @CreationTimestamp
    @Getter
    private ZonedDateTime created; // 생성일자
}
