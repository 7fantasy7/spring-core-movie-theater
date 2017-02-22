package ua.epam.spring.hometask.dao.impl;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

import ua.epam.spring.hometask.domain.*;

/**
 * @author Evgeny_Botyanovsky
 */
public class TicketDaoImplTest {

    private TicketDaoImpl ticketDao;

    private static Map<Long, Ticket> tickets;

    private User user1;
    private Event event1;

    private final LocalDateTime now = LocalDateTime.now();

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        ticketDao = new TicketDaoImpl();
        final Field ticketsField = TicketDaoImpl.class.getDeclaredField("tickets");
        ticketsField.setAccessible(true);

        user1 = new User(1L).setFirstName("first");
        final User user2 = new User(2L).setFirstName("second");
        final User user3 = new User(3L).setFirstName("third");

        final NavigableSet<LocalDateTime> event1Dates = new TreeSet<>();
        event1Dates.add(now);
        event1Dates.add(now.plusDays(3));
        event1Dates.add(now.plusDays(5).plusMinutes(20));

        final NavigableSet<LocalDateTime> event2Dates = new TreeSet<>();
        event2Dates.add(now.minusDays(3));
        event2Dates.add(now.plusDays(5));
        event2Dates.add(now.plusDays(10).minusHours(2));
        final NavigableMap<LocalDateTime, Auditorium> eventAuditoriums = new TreeMap<>();
        eventAuditoriums.put(now, new Auditorium().setName("Test auditorium1")
                .setNumberOfSeats(50).setVipSeats(newHashSet(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L)));
        eventAuditoriums.put(now, new Auditorium().setName("Test auditorium2")
                .setNumberOfSeats(30).setVipSeats(newHashSet(4L, 5L, 6L, 7L, 8L)));

        event1 = new Event(1L).setAirDates(event1Dates).setBasePrice(45.20)
                .setName("Test event").setRating(EventRating.MID).setAuditoriums(eventAuditoriums);
        final Event event2 = new Event(2L).setAirDates(event2Dates).setBasePrice(84)
                .setName("Another event").setRating(EventRating.HIGH).setAuditoriums(eventAuditoriums);

        tickets = new HashMap<Long, Ticket>() {{
            put(1L, new Ticket(user1, event1, now, 1));
            put(2L, new Ticket(user2, event1, now, 5));
            put(3L, new Ticket(user2, event1, now, 4));
            put(4L, new Ticket(user3, event2, now.minusDays(3), 2));
            put(5L, new Ticket(user1, event2, now.plusDays(5), 14));
        }};

        ticketsField.set(ticketDao, tickets);
    }

    @Test
    public void shouldReturnById() {
        // when
        final Ticket ticketById = ticketDao.getById(1L);

        // then
        assertEquals(tickets.get(1L), ticketById);
    }

    @Test
    public void shouldReturnByUser() {
        // when
        final Collection<Ticket> ticketsByUser = ticketDao.getUserTickets(user1);

        // then
        assertEquals(newHashSet(tickets.get(1L), tickets.get(5L)), newHashSet(ticketsByUser));
    }

    @Test
    public void shouldReturnPurchasedTicketsForEvent() {
        // when
        final Collection<Ticket> ticketsByEvent = ticketDao.getPurchasedTicketsForEvent(event1, now);

        // then
        assertEquals(ticketsByEvent.size(), 3);
        assertEquals(newHashSet(tickets.get(1L), tickets.get(2L), tickets.get(3L)), newHashSet(ticketsByEvent));
    }

    @Test
    public void shouldReturnAllTickets() {
        // when
        final Collection<Ticket> allTickets = ticketDao.getAll();

        // then
        assertEquals(newHashSet(tickets.values()), newHashSet(allTickets));
    }

}
