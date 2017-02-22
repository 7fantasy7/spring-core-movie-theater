package ua.epam.spring.hometask.service.impl;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.junit.Before;
import org.junit.Test;

import ua.epam.spring.hometask.dao.impl.TicketDaoImpl;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.strategy.impl.BirthdayDiscountStrategy;
import ua.epam.spring.hometask.strategy.impl.WholesaleDiscountStrategy;

/**
 * @author Evgeny_Botyanovsky
 */
public class DiscountServiceImplTest {

    private DiscountServiceImpl discountService;

    private BirthdayDiscountStrategy birthdayDiscountStrategy;
    private WholesaleDiscountStrategy wholesaleDiscountStrategy;

    private User user;
    private Event event;
    private LocalDateTime airDateTime;
    private long numberOfTickets;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        final TicketDaoImpl ticketDao = new TicketDaoImpl();
        birthdayDiscountStrategy = new BirthdayDiscountStrategy();
        wholesaleDiscountStrategy = new WholesaleDiscountStrategy(ticketDao);
        discountService = new DiscountServiceImpl(newHashSet(birthdayDiscountStrategy, wholesaleDiscountStrategy));

        final NavigableSet<LocalDateTime> event1Dates = new TreeSet<>();
        final LocalDateTime now = LocalDateTime.now();
        event1Dates.add(now);
        event1Dates.add(now.plusDays(3));
        event1Dates.add(now.plusDays(5).plusMinutes(20));
        final NavigableMap<LocalDateTime, Auditorium> eventAuditoriums = new TreeMap<>();
        eventAuditoriums.put(now, new Auditorium().setName("Test auditorium1")
                .setNumberOfSeats(50).setVipSeats(newHashSet(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L)));
        eventAuditoriums.put(now, new Auditorium().setName("Test auditorium2")
                .setNumberOfSeats(30).setVipSeats(newHashSet(4L, 5L, 6L, 7L, 8L)));
        event = new Event(1L).setAirDates(event1Dates).setBasePrice(45.20)
                .setName("Test event").setRating(EventRating.MID).setAuditoriums(eventAuditoriums);

        airDateTime = now;
        numberOfTickets = 55;
    }

    @Test
    public void shouldReturnMostDiscountForBirthday() {
        // given
        user = new User().setBirthDay(LocalDateTime.now().minusDays(4));

        // when
        final byte discount = discountService.getDiscount(user, event, airDateTime, numberOfTickets);

        // then
        assertEquals(maxDiscount(user, event, airDateTime, numberOfTickets), discount);
    }

    @Test
    public void shouldReturnMostDiscountForMultipleTickets() {
        // given
        user = new User().setBirthDay(LocalDateTime.now().minusDays(7));

        // when
        final byte discount = discountService.getDiscount(user, event, airDateTime, numberOfTickets);

        // then
        assertEquals(maxDiscount(user, event, airDateTime, numberOfTickets), discount);
    }

    private byte maxDiscount(@Nullable final User user, @Nonnull final Event event,
                             @Nonnull final LocalDateTime airDateTime, final long numberOfTickets) {
        return (byte) Math.max(birthdayDiscountStrategy.getDiscount(user, event, airDateTime, numberOfTickets),
                wholesaleDiscountStrategy.getDiscount(user, event, airDateTime, numberOfTickets));
    }

}
