package ua.epam.spring.hometask.runner;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.TreeMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.google.common.collect.Sets;

import ua.epam.spring.hometask.config.MainSpringConfig;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;

/**
 * Simple test for aspects, if they're working or not
 *
 * @author Evgeny_Botyanovsky
 */
public class Runner {

    public static void main(String[] args) {
        final ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(MainSpringConfig.class);

        /*
         * Getting service beans
         */
        final AuditoriumService auditoriumService = applicationContext.getBean(AuditoriumService.class);
        final EventService eventService = applicationContext.getBean(EventService.class);
        final BookingService bookingService = applicationContext.getBean(BookingService.class);

        /*
         * Ensure, that auditoriums were successfully autowired
         */
        auditoriumService.getAll().forEach(System.out::println);

        /*
         * Counter aspect: getByName
         */
        final LocalDateTime now = LocalDateTime.now();
        final Event event = new Event()
                .setName("event" + new Random(System.nanoTime()).nextInt())
                .setBasePrice(40).setRating(EventRating.HIGH)
                .setAuditoriums(new TreeMap<LocalDateTime, Auditorium>() {{
                    put(now, auditoriumService.getByName("Red"));
                }});

        eventService.save(event);
        System.out.println(eventService.getByName("event"));
        System.out.println(eventService.getByName("event555"));

        /*
         * Counter aspect: bookTickets
         * Lucky winner aspect: bookTickets, aspect may get or not get discount based on random
         */
        bookingService.bookTickets(Sets.newHashSet(new Ticket(new User().setFirstName("anotherOne").setLastName("botyanovsky"),
                new Event().setName("event" + new Random(System.nanoTime()).nextInt()), LocalDateTime.now(), 999L)));

        /*
         * Counter aspect: getTicketsPrice,
         * Discount aspect: both strategies should be called
         */
        System.out.println(bookingService.getTicketsPrice(
                event, now, null, Sets.newHashSet(1L, 2L, 3L)));

        /*
         * Counters should be fetched from DB
         */
        System.out.println(eventService.getByName(event.getName()));
        System.out.println(eventService.getByName(event.getName()));
        System.out.println(eventService.getByName(event.getName()));
        System.out.println(eventService.getByName(event.getName()).getEventStatistics().getAccessedByName());
    }

}
