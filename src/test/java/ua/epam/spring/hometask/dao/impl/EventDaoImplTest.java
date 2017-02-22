package ua.epam.spring.hometask.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

import static com.google.common.collect.Sets.newHashSet;

import org.junit.Before;
import org.junit.Test;

import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;

/**
 * @author Evgeny_Botyanovsky
 */
public class EventDaoImplTest {

    private EventDaoImpl eventDao;

    private static Map<Long, Event> events;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        eventDao = new EventDaoImpl();
        final Field eventsField = EventDaoImpl.class.getDeclaredField("events");
        eventsField.setAccessible(true);

        final LocalDateTime now = LocalDateTime.now();
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

        events = new HashMap<Long, Event>() {{
            put(1L, new Event(1L).setAirDates(event1Dates).setBasePrice(45.20)
                    .setName("Test event").setRating(EventRating.MID).setAuditoriums(eventAuditoriums));
            put(2L, new Event(2L).setAirDates(event2Dates).setBasePrice(84)
                    .setName("Another event").setRating(EventRating.HIGH).setAuditoriums(eventAuditoriums));
        }};
        eventsField.set(eventDao, events);
    }

    @Test
    public void shouldReturnById() {
        // when
        final Event eventById = eventDao.getById(1L);

        // then
        assertEquals(events.get(1L), eventById);
    }

    @Test
    public void shouldReturnByName() {
        // when
        final Event eventByName = eventDao.getByName("Test event");

        // then
        assertEquals(events.get(1L), eventByName);
    }

    @Test
    public void shouldReturnNullByNotExistingName() {
        // when
        final Event eventByNotExistingName = eventDao.getByName("Not existing name");

        // then
        assertNull(eventByNotExistingName);
    }

    @Test
    public void shouldReturnAllEvents() {
        // when
        final Collection<Event> allEvents = eventDao.getAll();

        // then
        assertEquals(newHashSet(events.values()), newHashSet(allEvents));
    }

    @Test
    public void shouldReturnNextEventsIfTheyExist() {
        // when
        final Collection<Event> allEvents = eventDao.getNextEvents(LocalDateTime.now().plusDays(999));

        // then
        assertEquals(newHashSet(events.values()), newHashSet(allEvents));
    }

    @Test
    public void shouldNotReturnNextEventsIfTheyAreNotExist() {
        // when
        final Collection<Event> allEvents = eventDao.getNextEvents(LocalDateTime.now().minusDays(999));

        // then
        assertTrue(allEvents.isEmpty());
    }

    @Test
    public void shouldReturnEventsForDateRange() {
        // when
        final Collection<Event> allEvents = eventDao.getForDateRange(LocalDateTime.now().minusDays(2), LocalDateTime.now().plusDays(2));

        // then
        assertEquals(1, allEvents.size());
        assertEquals(events.get(1L), allEvents.iterator().next());
    }

}
