package com.exam.toylocal.domain.book.kakao;

import org.junit.Assert;
import org.junit.Test;

import java.time.ZonedDateTime;

/**
 * @author hahms
 * @since 15/11/2018
 */
public class KakaoBookTest {

    @Test
    public void testTimeParse() {
        KakaoBook kakaoBook = new KakaoBook();
        kakaoBook.setDatetime("2018-12-25T13:00:00.000+09:00");
        ZonedDateTime time = kakaoBook.getTime();

        Assert.assertNotNull(time);
        Assert.assertEquals(1545710400000L, time.toInstant().toEpochMilli());
    }
}