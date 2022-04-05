package com.aikka.restaurant.filter;

import com.aikka.restaurant.dao.UserDAO;
import com.aikka.restaurant.service.jwt.JwtService;
import com.aikka.restaurant.service.jwt.TokenDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    @Autowired
    JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("Logging request [{}, {}]", request.getMethod(), request.getRequestURI());
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearer == null || bearer.trim().isEmpty() || bearer.length() <=6) {
            RestaurantUserPrinciple restaurantUserPrinciple = new RestaurantUserPrinciple(Set.of(), null, "ANONYMOUS");
            SecurityContextHolder.getContext().setAuthentication(restaurantUserPrinciple);
            doFilter(request, response, filterChain);
        } else {
            // bearer token......
            TokenDetail user = jwtService.verifyAndReturnUserId(bearer);
            logger.info("Found valid user: {}", user);
            Set<String> authorities = Set.of("USER");

            // we assume user with id 1 is the manager
            if (user.getUserId() == 1) {
                authorities = Set.of("USER", "ADMIN");
            }
            RestaurantUserPrinciple restaurantUserPrinciple = new RestaurantUserPrinciple(authorities, user.getUserId(), user.getUsername());
            SecurityContextHolder.getContext().setAuthentication(restaurantUserPrinciple);
            filterChain.doFilter(request, response);
        }
    }
}
