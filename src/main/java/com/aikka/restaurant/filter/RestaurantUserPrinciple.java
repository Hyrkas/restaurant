package com.aikka.restaurant.filter;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.io.Serializable;
import java.util.Collections;

public class RestaurantUserPrinciple extends AbstractAuthenticationToken implements Serializable {
    public RestaurantUserPrinciple() {
        super(Collections.emptyList());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this;
    }
}
