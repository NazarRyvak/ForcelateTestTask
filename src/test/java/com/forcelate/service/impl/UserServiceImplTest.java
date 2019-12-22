package com.forcelate.service.impl;

import com.forcelate.dto.UserRegistrationDto;
import com.forcelate.exception.InvalidEmailException;
import com.forcelate.exception.InvalidPasswordException;
import com.forcelate.exception.UserAlreadyExistException;
import com.forcelate.repository.UserRepository;
import com.forcelate.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void registrationFailedByInvalidEmail() {
        UserRegistrationDto userRegistrationDto = UserRegistrationDto
                .builder()
                .email("test")
                .name("test")
                .age(23)
                .password("test")
                .build();
        Throwable thrown = catchThrowable(() -> userService.registration(userRegistrationDto));
        assertThat(thrown).isInstanceOf(InvalidEmailException.class);
    }

    @Test
    public void registrationFailedByInvalidPassword() {
        UserRegistrationDto userRegistrationDto = UserRegistrationDto
                .builder()
                .email("test@gmail.com")
                .name("test")
                .age(23)
                .password("1")
                .build();
        Throwable thrown = catchThrowable(() -> userService.registration(userRegistrationDto));
        assertThat(thrown).isInstanceOf(InvalidPasswordException.class);
    }

    @Test
    public void registrationFailedByUserAlreadyExist() {
        UserRegistrationDto userRegistrationDto = UserRegistrationDto
                .builder()
                .email("test@gmail.com")
                .name("test")
                .age(23)
                .password("1111a")
                .build();
        if(userRepository.findUserByEmail(userRegistrationDto.getEmail())==null) {
            userService.registration(userRegistrationDto);
        }
        Throwable thrown = catchThrowable(() -> userService.registration(userRegistrationDto));
        assertThat(thrown).isInstanceOf(UserAlreadyExistException.class);
    }
}
