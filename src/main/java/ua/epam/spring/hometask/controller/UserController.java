package ua.epam.spring.hometask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ua.epam.spring.hometask.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user");
        modelAndView.addObject("users", userService.getAll());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, params = "email")
    public ModelAndView getByEmail(@RequestParam String email) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user");
        modelAndView.addObject("user", userService.getUserByEmail(email));
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, params = "id")
    public ModelAndView getById(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user");
        modelAndView.addObject("user", userService.getById(id));
        return modelAndView;
    }

}
