package ua.epam.spring.hometask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.spring.hometask.service.AuditoriumService;

@Controller
@RequestMapping("/auditorium")
public class AuditoriumController {

    @Autowired
    private AuditoriumService auditoriumService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auditorium");
        modelAndView.addObject("auditoriums", auditoriumService.getAll());
        return modelAndView;
    }

    @RequestMapping(params = "name")
    public ModelAndView getByName(@RequestParam String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auditorium");
        modelAndView.addObject("auditoriums", auditoriumService.getAll());
        modelAndView.addObject("auditorium", auditoriumService.getByName(name));
        return modelAndView;
    }

}