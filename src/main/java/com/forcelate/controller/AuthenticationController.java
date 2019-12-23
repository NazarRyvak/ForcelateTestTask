package com.forcelate.controller;


import com.forcelate.constant.ExceptionMessage;
import com.forcelate.dto.AuthenticationDto;
import com.forcelate.dto.UserInfoDto;
import com.forcelate.entity.User;
import com.forcelate.exception.IncorrectPasswordException;
import com.forcelate.exception.NotFoundException;
import com.forcelate.security.CookieProvider;
import com.forcelate.security.JWTTokenProvider;
import com.forcelate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private JWTTokenProvider jwtTokenProvider;
    private UserService userService;
    private CookieProvider cookieProvider;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JWTTokenProvider jwtTokenProvider,
                                    UserService userService,
                                    CookieProvider cookieProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.cookieProvider = cookieProvider;
    }

    @PostMapping("/sign-in")
    public void login(@RequestBody AuthenticationDto authenticationDto, HttpServletResponse response) {
        User user = userService.findUserByEmail(authenticationDto.getEmail());
        if (user != null) {
            if (userService.checkPasswordMatches(user.getId(), authenticationDto.getPassword())) {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(authenticationDto.getEmail(),
                                authenticationDto.getPassword()));
                Cookie cookie = cookieProvider.createCookie(
                        "jwt",
                        jwtTokenProvider.generateToken(user.getId(), user.getEmail()));
                response.addCookie(cookie);
            } else {
                throw new IncorrectPasswordException(ExceptionMessage.INCORRECT_PASSWORD);
            }
        } else {
            throw new NotFoundException(ExceptionMessage.USER_NOT_FOUND_BY_EMAIL + user.getEmail());
        }
    }
}
