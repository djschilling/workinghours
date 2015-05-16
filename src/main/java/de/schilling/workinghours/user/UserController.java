package de.schilling.workinghours.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Raphael Schilling
 * @author David Schilling - davejs92@gmail.com
 */
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "users", method = RequestMethod.POST)
    public User create(@RequestBody @Valid User user) {

        return userService.createUser(user.getUsername(), user.getPassword(), user.getRole());
    }

    @RequestMapping(value = "users", method = GET)
    public List<User> getAll() {

        return userService.getAllUsers();
    }

    @RequestMapping(value = "whoami", method = GET)
    public User whoami() {

        return userService.getCurrentlyLoggedIn();
    }


}
