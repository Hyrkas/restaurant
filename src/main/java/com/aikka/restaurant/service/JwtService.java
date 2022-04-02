package com.aikka.restaurant.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtService {

    public String issueToken(Integer userId) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        ZonedDateTime issuedDate = ZonedDateTime.now();
        ZonedDateTime expirationDate = issuedDate.plusHours(12);
        return JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withIssuer("https://aikka.com")
                .withSubject(String.valueOf(userId))
                .withIssuedAt(Date.from(issuedDate.toInstant()))
                .withExpiresAt(Date.from(expirationDate.toInstant()))
                .sign(algorithm);
    }

    public Integer verifyAndReturnUserId(String bearer) {
        String jwt = bearer.substring(7);
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("https://aikka.com")
                .build();
        DecodedJWT verified = verifier.verify(jwt);
        return Integer.valueOf(verified.getSubject());
    }
}
