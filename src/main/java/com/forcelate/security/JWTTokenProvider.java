package com.forcelate.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTTokenProvider {

    @Value("${secret.key.for.token}")
    private String SECRET_KEY;

    @Value("${expired.time.token}")
    private long EXPIRED_TIME_TOKEN;

    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    public String generateToken(int id, String email) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("id", id);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 6000000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

}
