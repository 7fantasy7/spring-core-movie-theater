package ua.epam.spring.hometask.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.rest.exception.NotFoundException;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/booking", produces = {"application/json", "application/pdf"})
public class BookingRestController {

    private BookingService bookingService;
    private UserService userService;
    private EventService eventService;

    @Autowired
    public BookingRestController(BookingService bookingService, UserService userService, EventService eventService) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.eventService = eventService;
    }

    @RequestMapping(path = "/price", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public double getTicketsPrice(@RequestParam long eventId, LocalDateTime dateTime,
                                  long userId, Set<Long> seats) throws NotFoundException {
        User user = userService.getById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        Event event = eventService.getById(eventId);
        if (event == null) {
            throw new NotFoundException("Event not found");
        }
        return bookingService.getTicketsPrice(event, dateTime, user, seats);
    }

    @RequestMapping(path = "/book-tickets", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.OK)
    public void bookTickets(@RequestParam Set<Ticket> tickets) throws NotFoundException {
        bookingService.bookTickets(tickets);
    }

    @RequestMapping(path = "/tickets/{eventId}", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public Collection<Ticket> getTicketsForEvent(@PathVariable long eventId, @RequestParam LocalDateTime dateTime) throws NotFoundException {
        Event event = eventService.getById(eventId);
        if (event == null) {
            throw new NotFoundException("Event not found");
        }
        return bookingService.getPurchasedTicketsForEvent(event, dateTime);
    }

}
