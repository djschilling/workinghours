package de.schilling.workinghours.user;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.List;


/**
 * David Schilling - davejs92@gmail.com.
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentlyLoggedIn() {

        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }


    @Override
    public User createUser(String username, String password, String role) {

        User entity = new User(username, new StandardPasswordEncoder().encode(password), role);

        return userRepository.save(entity);
    }


    @Override
    public void changePassword(String username, String password) {

        User user = userRepository.findOne(username);
        user.setPassword(new StandardPasswordEncoder().encode(password));
        userRepository.save(user);
    }
}
