package com.aikka.restaurant.filter;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Set;

@Component
@RequestScope
public class RestaurantPrincipalFactory extends AbstractFactoryBean<RestaurantUserPrinciple> {

    @Override
    public Class<?> getObjectType() {
        return RestaurantUserPrinciple.class;
    }

    @Override
    protected RestaurantUserPrinciple createInstance() throws Exception {
        try {
            return (RestaurantUserPrinciple) SecurityContextHolder.getContext().getAuthentication();
        } catch (Exception e) {
            return new RestaurantUserPrinciple(Set.of(), null, "ANONYMOUS");
        }
    }
}
