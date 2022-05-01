package com.gmail.tinstefanic.minesweeperweb.services;

import com.gmail.tinstefanic.minesweeperweb.dtos.UserDto;
import com.gmail.tinstefanic.minesweeperweb.entities.User;
import com.gmail.tinstefanic.minesweeperweb.exceptions.UserAlreadyExistsException;
import com.gmail.tinstefanic.minesweeperweb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for adding new user to database.
 */
@Service
public class RegisterUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Given information about user registers new user, unless user with the same username already exists,
     * in that case UserAlreadyExistsException is thrown.
     * @param userDto Contains all information about user to register.
     * @return Newly created user.
     * @throws UserAlreadyExistsException Thrown if there is already user with the same username in the database.
     */
    public User registerNewUser(UserDto userDto) throws UserAlreadyExistsException {
        if (this.userRepository.existsById(userDto.getUsername())) {
            throw new UserAlreadyExistsException(
                    "There is already a user with username '" + userDto.getUsername() + "'"
            );
        }

        var user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));

        return this.userRepository.save(user);
    }
}
