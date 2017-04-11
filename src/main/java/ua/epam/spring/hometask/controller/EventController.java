package ua.epam.spring.hometask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ua.epam.spring.hometask.service.EventService;

@Controller
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("event");
        modelAndView.addObject("events", eventService.getAll());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, params = "name")
    public ModelAndView getByName(@RequestParam String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("event");
        modelAndView.addObject("event", eventService.getByName(name));
        return modelAndView;
    }

}
