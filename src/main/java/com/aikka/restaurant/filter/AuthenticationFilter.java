package com.aikka.restaurant.filter;

import com.aikka.restaurant.dao.UserDAO;
import com.aikka.restaurant.service.JwtService;
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

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    @Autowired
    UserDAO userDAO;
    @Autowired
    JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("Logging request [{}, {}]", request.getMethod(), request.getRequestURI());
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearer == null || bearer.trim().isEmpty() || bearer.length() <=6) {
            response.setStatus(401);
            filterChain.doFilter(request, response);
        } else {
            // bearer token......
            Integer userId = jwtService.verifyAndReturnUserId(bearer);
            logger.info("Found valid userId: {}", userId);
            RestaurantUserPrinciple restaurantUserPrinciple = new RestaurantUserPrinciple();
            SecurityContextHolder.getContext().setAuthentication(restaurantUserPrinciple);
        }
    }
}
