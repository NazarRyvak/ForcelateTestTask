package com.forcelate.service.impl;

import com.forcelate.constant.ExceptionMessage;
import com.forcelate.dto.UserInfoDto;
import com.forcelate.dto.UserRegistrationDto;
import com.forcelate.dto.UserWithArticleDto;
import com.forcelate.entity.User;
import com.forcelate.entity.enums.Color;
import com.forcelate.exception.InvalidEmailException;
import com.forcelate.exception.InvalidPasswordException;
import com.forcelate.exception.NotFoundException;
import com.forcelate.exception.UserAlreadyExistException;
import com.forcelate.mapper.UserInfoMapper;
import com.forcelate.mapper.UserRegistrationMapper;
import com.forcelate.mapper.UserWithArticleMapper;
import com.forcelate.repository.ArticleRepository;
import com.forcelate.repository.UserRepository;
import com.forcelate.security.JWTUser;
import com.forcelate.service.UserService;
import com.forcelate.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserInfoMapper userInfoMapper;
    private UserRepository userRepository;
    private ArticleRepository articleRepository;
    private UserWithArticleMapper userWithArticleMapper;
    private UserRegistrationMapper userRegistrationMapper;
    private Validator validator;

    @Autowired
    public UserServiceImpl(UserInfoMapper userInfoMapper,
                           UserRepository userRepository,
                           ArticleRepository articleRepository,
                           UserWithArticleMapper userWithArticleMapper,
                           UserRegistrationMapper userRegistrationMapper,
                           Validator validator) {
        this.userInfoMapper = userInfoMapper;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.userWithArticleMapper = userWithArticleMapper;
        this.userRegistrationMapper = userRegistrationMapper;
        this.validator = validator;
    }

    @Override
    public void registration(UserRegistrationDto userRegistrationDto) {
        if (!validator.validateEmail(userRegistrationDto.getEmail())) {
            throw new InvalidEmailException(ExceptionMessage.INVALID_EMAIL);
        }
        if (!validator.validatePassword(userRegistrationDto.getPassword())) {
            throw new InvalidPasswordException(ExceptionMessage.INVALID_PASSWORD);
        }
        if (!(userRepository.findUserByEmail(userRegistrationDto.getEmail()) == null)) {
            throw new UserAlreadyExistException(ExceptionMessage.USER_ALREADY_EXIST
                    + userRegistrationDto.getEmail()
                    + "!!!");
        }
        User user = userRegistrationMapper.convertToEntity(userRegistrationDto);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public boolean checkPasswordMatches(int id, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = userRepository.getOne(id);
        return encoder.matches(password, user.getPassword());
    }

    @Override
    public List<UserInfoDto> findUsersWithAgeMoreThan(int age) {
        return userInfoMapper.convertToListDto(userRepository.findUsersByAgeGreaterThan(age));
    }

    @Override
    public List<UserWithArticleDto> findUsersWithArticleByArticleColor(String colorString) {
        Color color;
        try {
            color = Color.valueOf(colorString.substring(0, 1).toUpperCase() + colorString.substring(1).toLowerCase());
        } catch (IllegalArgumentException e) {
            throw new NotFoundException(ExceptionMessage.COLOR_NOT_FOUND);
        }
        return userWithArticleMapper.convertToListDto(articleRepository.findArticlesByColor(color));
    }

    @Override
    public Set<String> uniqueUserNamesWhereUsersArticleGreaterThan(int countArticle) {
        return userRepository.findAll()
                .stream()
                .filter((user -> user.getArticles().size() > countArticle))
                .map(user -> user.getName())
                .collect(Collectors.toSet());
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JWTUser jwtUser = (JWTUser) authentication.getPrincipal();
        return jwtUser.getUser();
    }

}
