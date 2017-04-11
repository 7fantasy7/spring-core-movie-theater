package ua.epam.spring.hometask.controller;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Sets;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @RequestMapping(method = RequestMethod.POST, value = "/ticket")
    public ModelAndView bookTicket(@RequestParam long userId, @RequestParam long eventId, @RequestParam long seatNo,
                                   @RequestParam LocalDateTime dateTime) {
        User user = userService.getById(userId);
        Event event = eventService.getById(eventId);
        if (user == null || event == null) {
            return null;
        }
        Set<Long> ticketList = bookingService.getPurchasedTicketsForEvent(event, dateTime).stream()
                .map(Ticket::getSeat)
                .collect(Collectors.toSet());
        if (!ticketList.contains(seatNo)) {
            bookingService.bookTickets(Sets.newHashSet(new Ticket().setUser(user).setEvent(event).setSeat(seatNo)));

            ModelAndView modelAndView = new ModelAndView("event");
            modelAndView.addObject("event", event);
            return modelAndView;
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/price")
    public ModelAndView getTicketsPrice(@RequestParam long userId, @RequestParam long eventId,
                                        @RequestParam Set<Long> seats, @RequestParam LocalDateTime dateTime) {
        User user = userService.getById(userId);
        Event event = eventService.getById(eventId);
        if (user == null || event == null) {
            return null;
        }
        final double ticketsPrice = bookingService.getTicketsPrice(event, dateTime, user, seats);

        ModelAndView modelAndView = new ModelAndView("tickets");
        modelAndView.addObject("price", ticketsPrice);
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/event-tickets")
    public ModelAndView getTicketsForEvent(@RequestParam long eventId, @RequestParam LocalDateTime dateTime) {
        Event event = eventService.getById(eventId);
        if (event == null) {
            return null;
        }
        final Collection<Ticket> purchasedTicketsForEvent = bookingService.getPurchasedTicketsForEvent(event, dateTime);

        ModelAndView modelAndView = new ModelAndView("tickets");
        modelAndView.addObject("tickets", purchasedTicketsForEvent);
        return modelAndView;
    }

}
