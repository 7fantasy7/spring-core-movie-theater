package ua.epam.spring.hometask.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.rest.exception.NotFoundException;
import ua.epam.spring.hometask.service.EventService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/event", produces = {"application/json"})
public class EventRestController {

    private final EventService eventService;

    @Autowired
    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Event addEvent(@ModelAttribute Event event) {
        return eventService.save(event);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public Collection<Event> getAllEvents() {
        return eventService.getAll();
    }

    @RequestMapping(path = "/{eventId}", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public Event getEventById(@PathVariable Long eventId) throws NotFoundException {
        Event event = eventService.getById(eventId);
        if (event == null) {
            throw new NotFoundException("Event not found");
        }
        return event;
    }

    @RequestMapping(path = "/{eventId}", method = RequestMethod.DELETE)
    @ResponseStatus(code = HttpStatus.OK)
    public Event deleteEvent(@PathVariable Long eventId) throws NotFoundException {
        Event event = eventService.getById(eventId);
        if (event == null) {
            throw new NotFoundException("Event not found");
        }
        eventService.remove(event);
        return event;
    }
}
