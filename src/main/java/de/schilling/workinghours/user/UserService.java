package de.schilling.workinghours.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * David Schilling - davejs92@gmail.com
 */
@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findOne(username);
    }

    public User getCurrentlyLoggedIn() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
         return userRepository.findAll();
    }

    public User save(String username, String password, String role) {
        User entity = new User(username, new StandardPasswordEncoder().encode(password), role);
        return userRepository.save(entity);
    }
}
