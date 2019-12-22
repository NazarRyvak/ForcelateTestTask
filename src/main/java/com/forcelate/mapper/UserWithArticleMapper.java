package com.forcelate.mapper;

import com.forcelate.dto.UserWithArticleDto;
import com.forcelate.entity.Article;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserWithArticleMapper {

    private ArticleInfoMapper articleInfoMapper;

    public UserWithArticleMapper(ArticleInfoMapper articleInfoMappe) {
        this.articleInfoMapper = articleInfoMappe;
    }

    public UserWithArticleDto convertToDto(Article entity) {
        return UserWithArticleDto.builder()
                .id(entity.getUser().getId())
                .name(entity.getUser().getName())
                .age(entity.getUser().getAge())
                .article(articleInfoMapper.convertToDto(entity))
                .build();
    }

    public List<UserWithArticleDto> convertToListDto(List<Article> articles){
        List<UserWithArticleDto> listDto = new ArrayList<>();
        for (Article article:articles) {
            listDto.add(convertToDto(article));
        }
        return listDto;
    }
}
