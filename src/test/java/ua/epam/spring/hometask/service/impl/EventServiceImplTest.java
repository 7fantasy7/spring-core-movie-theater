package ua.epam.spring.hometask.service.impl;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.dao.impl.EventDaoImpl;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;

/**
 * @author Evgeny_Botyanovsky
 */
public class EventServiceImplTest {

    private EventServiceImpl eventService;

    private Event event;

    private EventDao eventDao;

    @Before
    public void setUp() {
        eventDao = new EventDaoImpl();
        eventService = new EventServiceImpl(eventDao);

        event = new Event(10L).setRating(EventRating.HIGH).setName("event");
    }

    @Test
    public void shouldSaveEvent() {
        // given
        final int eventsCount = eventService.getAll().size();

        // when
        eventService.save(event);

        // then
        final int eventsCountAfterSave = eventService.getAll().size();
        assertEquals(eventsCount + 1, eventsCountAfterSave);
    }

    @Test
    public void shouldGetByName() {
        // given
        eventService.save(event);

        // when
        final Event eventFromDB = eventService.getByName("event");

        // then
        assertNotNull(eventFromDB);
    }

    @Test
    public void shouldRemove() {
        // given
        eventService.save(event);

        // when
        eventService.remove(event);

        // then
        final Event eventFromDB = eventService.getByName("event");
        assertNull(eventFromDB);
    }

    @Test
    public void shouldGetForDateRange() {
        // given
        final LocalDateTime now = LocalDateTime.now();
        final Event event1 = eventService.save(new Event(1L).setName("one").setAirDates(new TreeSet<LocalDateTime>() {{
            add(now.minusDays(2));
        }}));
        final Event event2 = eventService.save(new Event(2L).setName("two").setAirDates(new TreeSet<LocalDateTime>() {{
            add(now.minusDays(1));
        }}));
        final Event event3 = eventService.save(new Event(3L).setName("three").setAirDates(new TreeSet<LocalDateTime>() {{
            add(now);
        }}));
        final Event event4 = eventService.save(new Event(4L).setName("four").setAirDates(new TreeSet<LocalDateTime>() {{
            add(now.plusDays(2));
        }}));
        eventDao.saveMany(event1, event2, event3, event4);

        // when
        final Collection<Event> events = eventService.getForDateRange(now.minusDays(2).plusHours(10), now.plusDays(1));

        // then
        assertFalse(events.contains(event1));
        assertTrue(events.contains(event2));
        assertTrue(events.contains(event3));
        assertFalse(events.contains(event4));
    }

}
