package com.exam.toylocal.domain.book.kakao;

import com.exam.toylocal.domain.book.Book;
import com.exam.toylocal.domain.book.BookConverter;
import com.exam.toylocal.domain.book.BookService;
import com.exam.toylocal.domain.common.DataResponse;
import com.exam.toylocal.domain.common.Pagination;
import com.exam.toylocal.utils.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author hahms
 * @since 14/11/2018
 */
@Service
@Slf4j
public class KakaoBookService implements BookService {

    @Autowired
    KakaoProperties kakaoProperties;

    public DataResponse<List<Book>, Pagination> search(String query, Integer page, Integer size) {
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("query", query);
            params.add("title", "title");
            params.add("page", String.valueOf(page));
            params.add("size", String.valueOf(size));

            UriComponents uriComponents =
                    UriComponentsBuilder.fromUriString(kakaoProperties.getBookEndpoint())
                            .queryParams(params)
                            .build()
                            .encode();
            URI uri = uriComponents.toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, kakaoProperties.getToken());
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity request = new HttpEntity(headers);

            ResponseEntity<String> response = new RestTemplate().exchange(
                    uri,
                    HttpMethod.GET,
                    request,
                    String.class);

            List<Book> books = BookConverter.CONVERTER.toBook(bookParser(response.getBody()));
            Pagination pagination = pageParser(response.getBody());
            pagination.setPageCount(books.size());
            return new DataResponse<>(books, pagination);
        } catch (Exception e) {
            log.error("kakako search api fail");
        }

        return new DataResponse<>(null, null);
    }

    private List<KakaoBook> bookParser(String body) {
        if (StringUtils.isEmpty(body)) return Collections.emptyList();

        try {
            HashMap ret = JSONUtil.objectMapper().readValue(body, HashMap.class);
            if (ret.containsKey("documents")) {
                ArrayList<HashMap> documents = (ArrayList<HashMap>) ret.get("documents");
                return documents.stream()
                        .map(doc -> JSONUtil.objectMapper().convertValue(doc, KakaoBook.class))
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            log.error("KakaoBook parse error : {}", body);
        }
        return Collections.emptyList();
    }

    private Pagination pageParser(String body) {
        if (StringUtils.isEmpty(body)) return Pagination.builder().build();

        try {
            HashMap ret = JSONUtil.objectMapper().readValue(body, HashMap.class);
            if (ret.containsKey("meta")) {
                HashMap meta = (HashMap) ret.get("meta");

                // 카카오 책 검색은 검색결과로 제공 가능한 데이터만 보여지며
                // (total_count는 전체 데이터가 아님)
                // 1000 이상 검색이 되지 않는다.
                long totalCount = ((Integer) meta.get("pageable_count")).longValue();
                totalCount = totalCount > 1000 ? 1000 : totalCount;

                return Pagination.builder()
                        .totalCount(totalCount)
                        .pageCount(0)
                        .isNext(!(Boolean) meta.get("is_end"))
                        .build();
            }
        } catch (IOException e) {
            log.error("Pagination parse error : {}", body);
        }
        return Pagination.builder().build();
    }
}
