package com.aikka.restaurant.filter;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RestaurantUserPrinciple extends AbstractAuthenticationToken implements Serializable {
    private final String username;
    private final Integer userId;
    public RestaurantUserPrinciple(Set<String> authorities, Integer userId, String username) {
        super(transform(authorities));
        super.setAuthenticated(true);
        this.username = username;
        this.userId = userId;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return (AuthenticatedPrincipal) () -> username;
    }

    public Integer getUserId() {
        return userId;
    }

    private static List<? extends GrantedAuthority> transform(Set<String> authorities) {
        if (authorities == null || authorities.isEmpty()) return List.of();
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
