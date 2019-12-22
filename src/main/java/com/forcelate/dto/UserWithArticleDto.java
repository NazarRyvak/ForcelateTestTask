package com.forcelate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserWithArticleDto {

    private int id;

    private String name;

    private int age;

    private ArticleInfoDto article;
}
