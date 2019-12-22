package com.forcelate.repository;

import com.forcelate.entity.enums.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forcelate.entity.Article;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    List<Article> findArticlesByColor(Color color);
}
