package com.forcelate.service.impl;

import com.forcelate.dto.ArticleInfoDto;
import com.forcelate.entity.Article;
import com.forcelate.mapper.ArticleInfoMapper;
import com.forcelate.repository.ArticleRepository;
import com.forcelate.service.ArticleService;
import com.forcelate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository;
    private ArticleInfoMapper articleInfoMapper;
    private UserService userService;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository,
                              ArticleInfoMapper articleInfoMapper,
                              UserService userService) {
        this.articleRepository = articleRepository;
        this.articleInfoMapper = articleInfoMapper;
        this.userService = userService;
    }

    @Override
    public void save(ArticleInfoDto articleInfoDto) {
        articleInfoDto.setUserId(userService.getCurrentUser().getId());
        Article article = articleInfoMapper.convertToEntity(articleInfoDto);
        articleRepository.save(article);
    }
}
