package com.exam.toylocal.oauth.config;

import com.exam.toylocal.oauth.domain.user.LocalUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author hahms
 * @since 13/11/2018
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Value("${jwt.jks.fileName}")
    private String jksFileName;
    @Value("${jwt.jks.alias}")
    private String jwtAlias;
    @Value("${jwt.jks.pass}")
    private String jwtPass;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private OAuth2ClientDetails clientDetails;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        for (BaseClientDetails clientDetail : clientDetails.getClient()) {
            builder.
                    withClient(clientDetail.getClientId()).
                    authorizedGrantTypes("password", "refresh_token").
                    secret(clientDetail.getClientSecret()).
                    scopes(clientDetail.getScope().toArray(new String[0]));
        }
    }

    @Bean
    public BaseClientDetails clientDetails() {
        return new BaseClientDetails();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager).userDetailsService(userDetailsService);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new CustomTokenEnhancer();
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(new ClassPathResource(jksFileName), jwtPass.toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair(jwtAlias));
        return converter;
    }

    /*
     * JWT 토큰에 추가 정보
     */
    class CustomTokenEnhancer extends JwtAccessTokenConverter {
        @Override
        public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
            LocalUserDetails localUserDetails = (LocalUserDetails) authentication.getPrincipal();

            Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());

            info.put("userId", localUserDetails.getId());

            DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
            customAccessToken.setAdditionalInformation(info);

            return super.enhance(customAccessToken, authentication);
        }
    }

}
