package de.schilling.workinghours.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by raphael on 12.04.15.
 */
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "users", method = RequestMethod.POST)
    public User save(@RequestBody @Valid User user) {
        return userService.save(user);
    }

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public List<User> getAll() { return userService.getAllUsers();
    }


}
