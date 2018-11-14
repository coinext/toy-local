package com.exam.toylocal.oauth.domain.user;

import com.exam.toylocal.domain.user.User;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author hahms
 * @since 13/11/2018
 */
@Builder
public class LocalUserDetails implements UserDetails {

    private final User user;

    public Long getId() {
        return this.user.getId();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        // 현재 구현하지 않음
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getEnabled() == null ? false : user.getEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getEnabled() == null ? false : user.getEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getEnabled() == null ? false : user.getEnabled();
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled() == null ? false : user.getEnabled();
    }
}
