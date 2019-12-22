package com.forcelate.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Component
public class CookieProvider {

    @Value("${expired.time.for.cookie}")
    private int EXPIRED_TIME_FOR_COOKIE;

    public Cookie createCookie(String key, String token) {
        Cookie cookie = new Cookie(key, token);
        cookie.setMaxAge(EXPIRED_TIME_FOR_COOKIE);
        cookie.setPath("/");
        return cookie;
    }
}
