package de.schilling.workinghours.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Created by david on 16.05.15.
 */
public interface UserService extends UserDetailsService {
    User getCurrentlyLoggedIn();

    List<User> getAllUsers();

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    User createUser(String username, String password, String role);
}
