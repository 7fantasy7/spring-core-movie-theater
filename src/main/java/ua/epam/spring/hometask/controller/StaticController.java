package ua.epam.spring.hometask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/ticket")
    public String tickets() {
        return "ticket";
    }

}
