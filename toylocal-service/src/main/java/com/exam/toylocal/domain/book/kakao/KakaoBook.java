package com.exam.toylocal.domain.book.kakao;

import com.exam.toylocal.domain.book.Book;

import java.time.ZonedDateTime;

/**
 * @author hahms
 * @since 14/11/2018
 */
public class KakaoBook extends Book {

    public void setDatetime(String time) {
        setTime(ZonedDateTime.parse(time));
    }
}
