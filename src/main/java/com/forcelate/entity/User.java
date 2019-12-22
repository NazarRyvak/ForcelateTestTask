package com.forcelate.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "user")
@ToString(exclude = {"articles"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH})
    private List<Article> articles;
}
