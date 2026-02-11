package org.konge.taskmanagementapp.api.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final long EXPIRATION_TIME; //24h
    private final SecretKey SECRET_KEY;

    public JwtService(
            @Value("${app.jwt.secret}") String secretString,
            @Value("${app.jwt.expiration_time}") long expirationTime
    ) {
        EXPIRATION_TIME = expirationTime;
        SECRET_KEY = Keys.hmacShaKeyFor(secretString.getBytes());
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }
}