package com.exam.toylocal.config;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author hahms
 * @since 13/11/2018
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${jwt.publicKey}")
    String publicKey;

    private String resourceId = "toylocal-resource";

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        ((DefaultAccessTokenConverter) converter.getAccessTokenConverter()).setUserTokenConverter(
                new DefaultUserAuthenticationConverter() {
                    @Override
                    public Authentication extractAuthentication(Map<String, ?> map) {
                        Authentication authentication = super.extractAuthentication(map);
                        ((UsernamePasswordAuthenticationToken) authentication).setDetails(map);
                        return authentication;
                    }
                }
        );

        Resource resource = new ClassPathResource(publicKey);
        String publicKey;
        try {
            publicKey = IOUtils.toString(resource.getInputStream(), Charset.defaultCharset());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        converter.setVerifierKey(publicKey);
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenService() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//                .antMatchers("/v1/**").permitAll()
                .anyRequest().authenticated()
                .and().cors()
                .and().csrf().disable();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(resourceId);
    }
}
