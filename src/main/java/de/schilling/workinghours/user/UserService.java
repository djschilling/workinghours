package de.schilling.workinghours.user;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;


/**
 * David Schilling - davejs92@gmail.com.
 */
public interface UserService {

    User getCurrentlyLoggedIn();


    List<User> getAllUsers();


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    User createUser(String username, String password, String role);


    @PreAuthorize("#username == principal.username")
    void changePassword(String username, String password);
}
