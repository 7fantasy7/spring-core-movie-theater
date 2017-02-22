package ua.epam.spring.hometask.strategy.impl;

import java.time.LocalDateTime;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.User;

/**
 * @author Evgeny_Botyanovsky
 */
public class BirthdayDiscountStrategyTest {

    private BirthdayDiscountStrategy birthdayDiscountStrategy;

    private Event event;

    private LocalDateTime now = LocalDateTime.now();

    @Before
    public void setUp() {
        final TreeSet<LocalDateTime> times = new TreeSet<LocalDateTime>() {{
            add(now);
            add(now.plusDays(10));
        }};

        birthdayDiscountStrategy = new BirthdayDiscountStrategy();

        event = new Event(1L).setBasePrice(10).setRating(EventRating.MID).setAirDates(times);
    }

    @Test
    public void shouldGiveBirthdayDiscountBecauseItsFourDayBeforeEvent() {
        // given
        final User user = new User().setBirthDay(LocalDateTime.now().minusDays(4));

        // when
        final byte discount = birthdayDiscountStrategy.getDiscount(user, event, now, 1);

        // then
        assertEquals(5, discount);
    }

    @Test
    public void shouldNotDependOnTicketCount() {
        // given
        final User user = new User().setBirthDay(LocalDateTime.now().minusDays(4));

        // when
        final byte discount = birthdayDiscountStrategy.getDiscount(user, event, now, 1234567);

        // then
        assertEquals(5, discount);
    }

}
