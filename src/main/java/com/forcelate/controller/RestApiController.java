package com.forcelate.controller;

import java.util.List;
import java.util.Set;

import com.forcelate.dto.ArticleInfoDto;
import com.forcelate.dto.UserInfoDto;
import com.forcelate.dto.UserRegistrationDto;
import com.forcelate.dto.UserWithArticleDto;
import com.forcelate.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.forcelate.entity.User;
import com.forcelate.service.UserService;

@RestController
@RequestMapping("/api")
public class RestApiController {

	private UserService userService;
	private ArticleService articleService;

	@Autowired
	public RestApiController(UserService userService,ArticleService articleService){
	    this.userService = userService;
	    this.articleService = articleService;
    }

	@PostMapping("/registration")
	public HttpStatus registration(@RequestBody UserRegistrationDto dto) {
		userService.registration(dto);
		return HttpStatus.OK;
	}

	@PostMapping("/add_article")
	public HttpStatus addArticle(@RequestBody ArticleInfoDto dto) {
		articleService.save(dto);
		return HttpStatus.OK;
	}
	
	@GetMapping("/users_by_age_greater_than/{age}")
	public List<UserInfoDto> findUserWithAgeMoreThan(@PathVariable("age") int age){
		return userService.findUsersWithAgeMoreThan(age);
	}

	@GetMapping("/users_and_article/{color}")
	public List<UserWithArticleDto> findUsersWithArticleByArticleColor(@PathVariable("color") String color){
		return userService.findUsersWithArticleByArticleColor(color);
	}

	@GetMapping("/names_where_count_of_article_greater_than/{count}")
	public Set<String> findUsersWithArticleByArticleColor(@PathVariable("count") int count){
		return userService.uniqueUserNamesWhereUsersArticleGreaterThan(count);
	}
}
