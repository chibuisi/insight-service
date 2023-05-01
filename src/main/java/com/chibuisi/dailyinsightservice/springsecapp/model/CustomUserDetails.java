package com.chibuisi.dailyinsightservice.springsecapp.model;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Map;

@Builder
public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String id;
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String givenName;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> additionalClaims;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Map<String, Object> getAdditionalClaims() {
        return additionalClaims;
    }
}

