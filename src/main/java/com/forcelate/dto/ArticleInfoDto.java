package com.forcelate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleInfoDto {

    private int id;

    private String text;

    private String color;

    private int userId;
}
