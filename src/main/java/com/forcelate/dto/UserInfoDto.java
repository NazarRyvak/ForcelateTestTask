package com.forcelate.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {

    private int id;

    private String name;

    private int age;

    private List<ArticleInfoDto> articles;
}
