package com.gmail.tinstefanic.minesweeperweb.services;

import com.gmail.tinstefanic.minesweeperweb.entities.User;
import com.gmail.tinstefanic.minesweeperweb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DefaultUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public DefaultUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = this.userRepository.findById(username);
        if (optUser.isPresent()) return optUser.get();
        else throw new UsernameNotFoundException(
                "User with username '" + username + "' doesn't exist in database."
        );
    }
}
