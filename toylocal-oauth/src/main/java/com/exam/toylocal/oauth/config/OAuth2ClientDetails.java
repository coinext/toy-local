package com.exam.toylocal.oauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hahms
 * @since 13/11/2018
 */
@Component
@ConfigurationProperties("security.oauth2")
public class OAuth2ClientDetails {
    private List<BaseClientDetails> client;

    public OAuth2ClientDetails(){}

    public List<BaseClientDetails> getClient() {
        return client;
    }

    public void setClient(List<BaseClientDetails> client) {
        this.client = client;
    }
}
