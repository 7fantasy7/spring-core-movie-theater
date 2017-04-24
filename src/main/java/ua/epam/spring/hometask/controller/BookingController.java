package ua.epam.spring.hometask.controller;


import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/booking")
@Transactional
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @RequestMapping(method = RequestMethod.POST, value = "/ticket")
    @PreAuthorize("hasAnyRole('BOOKING_MANAGER', 'REGISTERED_USER')")
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

            ModelAndView modelAndView = new ModelAndView("authorized/event");
            modelAndView.addObject("event", event);
            return modelAndView;
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/price")
    @PreAuthorize("hasAnyRole('BOOKING_MANAGER', 'REGISTERED_USER')")
    public ModelAndView getTicketsPrice(@RequestParam long userId, @RequestParam long eventId,
                                        @RequestParam Set<Long> seats, @RequestParam LocalDateTime dateTime) {
        User user = userService.getById(userId);
        Event event = eventService.getById(eventId);
        if (user == null || event == null) {
            return null;
        }
        final double ticketsPrice = bookingService.getTicketsPrice(event, dateTime, user, seats);

        ModelAndView modelAndView = new ModelAndView("managing/ticket");
        modelAndView.addObject("price", ticketsPrice);
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/event-tickets")
    @PreAuthorize("hasAnyRole('BOOKING_MANAGER', 'REGISTERED_USER')")
    public ModelAndView getTicketsForEvent(@RequestParam long eventId, @RequestParam LocalDateTime dateTime) {
        Event event = eventService.getById(eventId);
        if (event == null) {
            return null;
        }
        final Collection<Ticket> purchasedTicketsForEvent = bookingService.getPurchasedTicketsForEvent(event, dateTime);

        ModelAndView modelAndView = new ModelAndView("managing/ticket");
        modelAndView.addObject("tickets", purchasedTicketsForEvent);
        return modelAndView;
    }

    /*
     * Test method for account filling
     */
    @RequestMapping(method = RequestMethod.GET, value = "/test-user-acc")
    @ResponseStatus(value = HttpStatus.OK)
    public void testUserAccount(@RequestParam double diff) {
        User user = userService.getById(2L);
        user.getUserAccount().setMoney(user.getUserAccount().getMoney() + diff);
    }

}
