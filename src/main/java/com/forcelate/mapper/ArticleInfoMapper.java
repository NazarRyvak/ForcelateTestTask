package com.forcelate.mapper;

import com.forcelate.constant.ExceptionMessage;
import com.forcelate.dto.ArticleInfoDto;
import com.forcelate.entity.Article;
import com.forcelate.entity.enums.Color;
import com.forcelate.exception.NotFoundException;
import com.forcelate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArticleInfoMapper {

    private UserRepository userRepository;

    @Autowired
    public ArticleInfoMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Article convertToEntity(ArticleInfoDto dto) {
        Article article = Article.builder()
                .id(dto.getId())
                .text(dto.getText())
                .user(userRepository.getOne(dto.getUserId()))
                .build();
        try {
            article.setColor(Color.valueOf(
                    dto.getColor().substring(0, 1).toUpperCase()
                            + dto.getColor().substring(1).toLowerCase()));
        }catch (IllegalArgumentException e) {
            throw new NotFoundException(ExceptionMessage.COLOR_NOT_FOUND);
        }
        return article;
    }

    public ArticleInfoDto convertToDto(Article entity) {
        return ArticleInfoDto.builder()
                .id(entity.getId())
                .text(entity.getText())
                .color(entity.getColor().toString())
                .userId(entity.getUser().getId())
                .build();
    }

    public List<ArticleInfoDto> convertToListDto(List<Article> entities) {
        List<ArticleInfoDto> listDto = new ArrayList<>();
        for (Article entity : entities) {
            listDto.add(convertToDto(entity));
        }
        return listDto;
    }

    public List<Article> convertToListEntity(List<ArticleInfoDto> listDto) {
        List<Article> articles = new ArrayList<>();
        for (ArticleInfoDto dto : listDto) {
            articles.add(convertToEntity(dto));
        }
        return articles;
    }
}
