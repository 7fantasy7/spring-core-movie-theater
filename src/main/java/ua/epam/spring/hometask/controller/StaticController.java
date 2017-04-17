package ua.epam.spring.hometask.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticController {

    @RequestMapping("/")
    public String root() {
        return "login";
    }

    @RequestMapping("/index")
    @PreAuthorize("hasAnyRole('BOOKING_MANAGER', 'REGISTERED_USER')")
    public String index() {
        return "authorized/index";
    }

    @RequestMapping("/ticket")
    @PreAuthorize("hasRole('BOOKING_MANAGER')")
    public String tickets() {
        return "managing/ticket";
    }

}
