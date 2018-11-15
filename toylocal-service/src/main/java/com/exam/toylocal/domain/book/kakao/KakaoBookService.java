package com.exam.toylocal.domain.book.kakao;

import com.exam.toylocal.domain.book.Book;
import com.exam.toylocal.domain.book.BookConverter;
import com.exam.toylocal.utils.JSONUtil;
import lombok.extern.slf4j.Slf4j;
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
public class KakaoBookService {

    private String endpoint = "https://dapi.kakao.com/v3/search/book";
    private String kakaoToken = "KakaoAK fd4a6d07c15fb742ea94d8ffa2f2493e";

    public List<Book> search(String query) {
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("query", query);
            params.add("title", "title");

            UriComponents uriComponents =
                    UriComponentsBuilder.fromUriString(endpoint)
                            .queryParams(params)
                            .build()
                            .encode();
            URI uri = uriComponents.toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, kakaoToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity request = new HttpEntity(headers);

            ResponseEntity<String> response = new RestTemplate().exchange(
                    uri,
                    HttpMethod.GET,
                    request,
                    String.class);

            return BookConverter.CONVERTER.toBook(bookParser(response.getBody()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
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
            log.error("checkSuccess parse error : {}", body);
        }
        return Collections.emptyList();
    }
}
