package de.schilling.workinghours;

import de.schilling.workinghours.user.User;
import de.schilling.workinghours.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.StandardPasswordEncoder;

import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.PostConstruct;


/**
 * Created by david on 16.05.15.
 */
@Component
public class Startup {

    private final UserRepository userRepository;

    @Autowired
    public Startup(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @PostConstruct
    public void onStartup() {

        List<User> allUsers = userRepository.findAll();

        if (allUsers.size() == 0) {
            userRepository.save(new User("admin", new StandardPasswordEncoder().encode("admin"), "admin"));
        }
    }
}
