package ua.epam.spring.hometask.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.rest.exception.NotFoundException;
import ua.epam.spring.hometask.service.UserService;

import javax.xml.bind.ValidationException;
import java.util.Collection;

@RestController
@RequestMapping(path = "/api/user", produces = {"application/json"})
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    public User addUser(@ModelAttribute User user) throws ValidationException {
        return userService.save(user);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public Collection<User> getAllUsers() {
        return userService.getAll();
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public User getUserById(@PathVariable Long userId) throws NotFoundException {
        User user = userService.getById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        return user;
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.DELETE)
    @ResponseStatus(code = HttpStatus.OK)
    public User deleteUser(@PathVariable Long userId) throws NotFoundException {
        User user = userService.getById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        userService.remove(user);
        return user;
    }
}
