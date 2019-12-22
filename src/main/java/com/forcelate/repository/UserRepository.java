package com.forcelate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forcelate.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	List<User> findUsersByAgeGreaterThan(int age);

	User findUserByEmail(String email);
}
