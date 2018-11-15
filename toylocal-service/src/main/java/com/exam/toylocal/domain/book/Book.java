package com.exam.toylocal.domain.book;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author hahms
 * @since 15/11/2018
 */
@Data
public class Book {
    private List<String> authors;
    private String contents;
    private ZonedDateTime time;
    private String isbn;
    private Integer price;
    private String publisher;
    private Integer salePrice;
    private String status;
    private String thumbnail;
    private String title;
    private List<String> translators;
    private String url;
}
