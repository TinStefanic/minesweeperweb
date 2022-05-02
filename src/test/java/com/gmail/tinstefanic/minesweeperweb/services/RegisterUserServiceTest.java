package com.gmail.tinstefanic.minesweeperweb.services;

import com.gmail.tinstefanic.minesweeperweb.dtos.UserDto;
import com.gmail.tinstefanic.minesweeperweb.entities.User;
import com.gmail.tinstefanic.minesweeperweb.exceptions.UserAlreadyExistsException;
import com.gmail.tinstefanic.minesweeperweb.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
class RegisterUserServiceTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    RegisterUserService registerUserService;

    @BeforeEach
    void setUp() {
        this.registerUserService = new RegisterUserService(this.userRepository, this.passwordEncoder);
    }

    @Test
    @DisplayName("Should throw exception if user with same username already exists.")
    void shouldThrowExceptionIfUserWithSameUsernameAlreadyExistsTest() {
        String username = "user", password = "user";
        when(this.userRepository.existsById(username)).thenReturn(true);
        var userDto = new UserDto(username, password);

        assertThatExceptionOfType(UserAlreadyExistsException.class)
                .isThrownBy(() -> this.registerUserService.registerNewUser(userDto));
    }

    @Test
    @DisplayName("Should add new user.")
    void shouldAddNewUserTest() throws UserAlreadyExistsException {
        String username = "user", password = "user";
        when(this.userRepository.existsById(username)).thenReturn(false);
        var userDto = new UserDto(username, password);

        this.registerUserService.registerNewUser(userDto);

        verify(this.userRepository).save(any(User.class));
    }
}