package com.exam.toylocal.oauth.config;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author hahms
 * @since 13/11/2018
 */
public class WebSecurityConfigurationTest {
    @Test
    public void testPasswordEncoder() {
        BCryptPasswordEncoder bcr = new BCryptPasswordEncoder();
        String result = bcr.encode("abc!@#");
        System.out.println("=== " + result);
    }
}