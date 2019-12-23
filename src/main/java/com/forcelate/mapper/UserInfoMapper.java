package com.forcelate.mapper;

import com.forcelate.dto.UserInfoDto;
import com.forcelate.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserInfoMapper {

    private ArticleInfoMapper articleInfoDtoToArticleMapper;

    @Autowired
    public UserInfoMapper(ArticleInfoMapper articleInfoDtoToArticleMapper) {
        this.articleInfoDtoToArticleMapper = articleInfoDtoToArticleMapper;
    }

    public User convertToEntity(UserInfoDto dto) {
        return User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .name(dto.getName())
                .age(dto.getAge())
                .articles(articleInfoDtoToArticleMapper.convertToListEntity(dto.getArticles()))
                .build();
    }

    public UserInfoDto convertToDto(User entity) {
        return UserInfoDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .name(entity.getName())
                .age(entity.getAge())
                .articles(articleInfoDtoToArticleMapper.convertToListDto(entity.getArticles()))
                .build();
    }

    public List<User> convertToListEntity(List<UserInfoDto> listDto) {
        List<User> entities = new ArrayList<>();
        for (UserInfoDto dto : listDto) {
            entities.add(convertToEntity(dto));
        }
        return entities;
    }

    public List<UserInfoDto> convertToListDto(List<User> entities) {
        List<UserInfoDto> listDto = new ArrayList<>();
        for (User entity : entities) {
            listDto.add(convertToDto(entity));
        }
        return listDto;
    }
}
