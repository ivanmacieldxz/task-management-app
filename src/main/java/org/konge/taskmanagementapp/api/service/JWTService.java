package org.konge.taskmanagementapp.api.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JWTService {

    private final String SECRET_STRING;
    private final long EXPIRATION_TIME; //24h

    public JWTService(
            @Value("${app.jwt.secret}") String secretString,
            @Value("${app.jwt.expiration_time}") long expirationTime
    ) {
        SECRET_STRING = secretString;
        EXPIRATION_TIME = expirationTime;
    }

    public String generateToken(String email) {
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());;

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }
}