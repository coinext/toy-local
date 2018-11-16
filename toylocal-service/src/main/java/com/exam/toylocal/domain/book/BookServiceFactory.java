package com.exam.toylocal.domain.book;

import com.exam.toylocal.domain.book.kakao.KakaoBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author hahms
 * @since 16/11/2018
 */
@Component
public class BookServiceFactory {

    @Autowired
    private KakaoBookService kakaoBookService;

    public BookService getService(BookVendor vendor) {

        if (BookVendor.kakao.equals(vendor)) {
            return kakaoBookService;
        }

        return kakaoBookService;
    }
}
