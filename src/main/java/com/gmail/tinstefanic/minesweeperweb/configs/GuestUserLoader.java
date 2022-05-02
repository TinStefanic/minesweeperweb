package com.gmail.tinstefanic.minesweeperweb.configs;

import com.gmail.tinstefanic.minesweeperweb.entities.User;
import com.gmail.tinstefanic.minesweeperweb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class GuestUserLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        loadGuestUser();
    }

    private void loadGuestUser() {
        if (this.userRepository.count() == 0) {
            String username = "guest", password = "";
            var guest = new User(username, this.passwordEncoder.encode(password));

            userRepository.save(guest);
        }
    }
}
