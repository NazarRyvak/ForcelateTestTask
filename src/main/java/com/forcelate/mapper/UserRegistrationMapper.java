package com.forcelate.mapper;

import com.forcelate.dto.UserRegistrationDto;
import com.forcelate.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationMapper {

    public User convertToEntity(UserRegistrationDto dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .age(dto.getAge())
                .build();
    }
}
