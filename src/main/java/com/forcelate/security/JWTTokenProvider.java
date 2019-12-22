package com.forcelate.security;

import com.forcelate.constant.ExceptionMessage;
import com.forcelate.exception.JwtAuthenticationException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTTokenProvider {

    @Value("${secret.key.for.token}")
    private String secretKey;

    @Value("${expired.time.token}")
    private long expiredTime;

    private JWTUserDetailService jwtUserDetailsService;
    private CookieProvider cookieProvider;

    @Autowired
    public JWTTokenProvider(JWTUserDetailService jwtUserDetailService, CookieProvider cookieProvider){
        this.cookieProvider = cookieProvider;
        this.jwtUserDetailsService = jwtUserDetailService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String generateToken(int id, String email) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("id", id);

        Date now = new Date();
        Date validity = new Date(now.getTime()+expiredTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getAuthorities());
    }

    public String getUsername(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())){
                return false;
            }
            return true;
        }catch(JwtException | IllegalArgumentException e){
            throw new JwtAuthenticationException(ExceptionMessage.JWT_TOKEN_IS_EXPIRED);
        }
    }

    public String resolveToken(HttpServletRequest request, String key) {
        return cookieProvider.readCookie(request, key);
    }

}
