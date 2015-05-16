package de.schilling.workinghours;

import de.schilling.workinghours.user.User;
import de.schilling.workinghours.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by david on 16.05.15.
 */
@Component
public class Startup {

    private final UserService userService;

    @Autowired
    public Startup(UserService userService) {
        this.userService = userService;
    }


    @PostConstruct
    public void onStartup() {
        List<User> allUsers = userService.getAllUsers();
        if (allUsers.size() == 0) {
            userService.save("admin", "admin", "admin");
        }
    }
}
