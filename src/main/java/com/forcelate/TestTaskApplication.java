package com.forcelate;

import com.forcelate.entity.Article;
import com.forcelate.entity.User;
import com.forcelate.entity.enums.Color;
import com.forcelate.repository.ArticleRepository;
import com.forcelate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class TestTaskApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ArticleRepository articleRepository;
    private Random rd = new Random();

    public static void main(String[] args) {
        SpringApplication.run(TestTaskApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            addArticle();
            addUser();
        }
    }

    private void addUser() {
        List<Article> articles = articleRepository.findAll();
        if (userRepository.count() == 0) {
            List<String> firstNames = new ArrayList<String>();
            List<String> lastNames = new ArrayList<String>();
            try (BufferedReader inputStreamFirstName = new BufferedReader(new FileReader("src/main/resources/first_name.txt"));
                 BufferedReader inputStreamLastName = new BufferedReader(new FileReader("src/main/resources/last_name.txt"))) {
                String first_name;
                String last_name;
                while ((first_name = inputStreamFirstName.readLine()) != null) {
                    firstNames.add(first_name);
                }
                while ((last_name = inputStreamLastName.readLine()) != null) {
                    lastNames.add(last_name);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<User> users = new ArrayList<User>();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            for (int i = 0; i < 20; i++) {
                User user = new User();
                String name = firstNames.get(rd.nextInt(firstNames.size())) + lastNames.get(rd.nextInt(lastNames.size()));
                user.setName(name);
                user.setEmail(name.replaceAll(" ","") + "@gmail.com");
                user.setPassword(passwordEncoder.encode("1111"));
                user.setAge(rd.nextInt(80));
                userRepository.save(user);
                users.add(user);
            }

            //add article to user
            for (Article article : articles) {
                article.setUser(users.get(rd.nextInt(users.size())));
                articleRepository.save(article);
            }
        }
    }

    private void addArticle() {
        if (articleRepository.count() == 0) {
            for (int i = 1; i <= 100; i++) {
                Article article = new Article();
                article.setText("Article №" + i);
                article.setColor(getRandomColor());
                articleRepository.save(article);
            }
        }
    }

    private Color getRandomColor() {
        switch (rd.nextInt(3)) {
            case 0:
                return Color.Blue;
            case 1:
                return Color.Green;
            case 2:
                return Color.Red;
            case 3:
                return Color.White;
            default:
                return Color.Yellow;
        }
    }


}

