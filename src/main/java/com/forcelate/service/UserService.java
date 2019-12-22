package com.forcelate.service;

import com.forcelate.dto.UserInfoDto;
import com.forcelate.dto.UserRegistrationDto;
import com.forcelate.dto.UserWithArticleDto;
import com.forcelate.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    void registration(UserRegistrationDto userRegistrationDto);

    List<UserInfoDto> findUsersWithAgeMoreThan(int age);

    List<UserWithArticleDto> findUsersWithArticleByArticleColor(String color);

    Set<String> uniqueUserNamesWhereUsersArticleGreaterThan(int countArticle);

    User getCurrentUser();
}
