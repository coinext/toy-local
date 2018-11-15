package com.exam.toylocal.domain.book.kakao;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author hahms
 * @since 15/11/2018
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "kakao")
public class KakaoProperties {
    private String bookEndpoint;
    private String token;
}
