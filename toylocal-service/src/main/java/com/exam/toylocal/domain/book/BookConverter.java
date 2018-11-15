package com.exam.toylocal.domain.book;

import com.exam.toylocal.domain.book.kakao.KakaoBook;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author hahms
 * @since 15/11/2018
 */
@Mapper
public interface BookConverter {
    BookConverter CONVERTER = Mappers.getMapper(BookConverter.class);

    Book toBook(KakaoBook kakaoBook);
    List<Book> toBook(List<KakaoBook> kakaoBook);
}
