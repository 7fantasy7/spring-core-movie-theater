package ua.epam.spring.hometask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ua.epam.spring.hometask.service.AuditoriumService;

@Controller
@RequestMapping("/auditorium")
@Transactional
public class AuditoriumController {

    @Autowired
    private AuditoriumService auditoriumService;

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('BOOKING_MANAGER', 'REGISTERED_USER')")
    public ModelAndView getAll() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("authorized/auditorium");
        modelAndView.addObject("auditoriums", auditoriumService.getAll());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, params = "name")
    @PreAuthorize("hasAnyRole('BOOKING_MANAGER', 'REGISTERED_USER')")
    public ModelAndView getByName(@RequestParam String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("authorized/auditorium");
        modelAndView.addObject("auditorium", auditoriumService.getByName(name));
        return modelAndView;
    }

}
