package ua.epam.spring.hometask.strategy.impl;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import ua.epam.spring.hometask.dao.impl.TicketDaoImpl;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

/**
 * @author Evgeny_Botyanovsky
 */
public class WholesaleDiscountStrategyTest {

    private TicketDaoImpl ticketDao;

    private WholesaleDiscountStrategy wholesaleDiscountStrategy;

    private Event midEvent;
    private Event highEvent;

    private long idGenerator = 1;

    private LocalDateTime now = LocalDateTime.now();

    @Before
    public void setUp() {
        ticketDao = new TicketDaoImpl();

        final TreeSet<LocalDateTime> times = new TreeSet<LocalDateTime>() {{
            add(now);
            add(now.plusDays(10));
        }};

        wholesaleDiscountStrategy = new WholesaleDiscountStrategy(ticketDao);

        midEvent = new Event(idGenerator++).setBasePrice(10).setRating(EventRating.MID).setAirDates(times);
        highEvent = new Event(idGenerator++).setBasePrice(35).setRating(EventRating.HIGH).setAirDates(times);
    }

    @Test
    public void shouldGiveWholesaleDiscountForNonRegisteredUserIfBoughtTenTicketsOrMore() {
        // given
        final User guest = null;

        // when
        final byte discountPercent = wholesaleDiscountStrategy.getDiscount(guest, midEvent, now, 10);

        // then
        assertEquals(5, discountPercent);
    }

    @Test
    public void shouldNotGiveWholesaleDiscountForNonRegisteredUserIfBoughtLessThenTenTickets() {
        // given
        final User guest = null;

        // when
        final byte discountPercent = wholesaleDiscountStrategy.getDiscount(guest, midEvent, now, 5);

        // then
        assertEquals(0, discountPercent);
    }

    @Test
    public void shouldNotGiveWholesaleDiscountForRegisteredUserWithJustOneNumberOfTicket() {
        // given
        final User user = new User(idGenerator++)
                .setFirstName("first")
                .setLastName("last");

        // when
        final byte discountPercent = wholesaleDiscountStrategy.getDiscount(user, midEvent, now, 1);

        // then
        assertEquals(0, discountPercent);
    }

    @Test
    public void shouldGiveWholesaleDiscountForRegisteredUserWithJustOneNumberOfTicketAndNineInAPast() {
        // given
        final User user = new User(idGenerator++);
        user.setTickets(new TreeSet<Ticket>() {{
            for (long i = 1; i < 10; i++) {
                Ticket ticket = new Ticket(user, new Event(idGenerator++).setName(String.valueOf(i)), LocalDateTime.now(), i);
                ticket.setId(i);
                ticketDao.save(ticket);
                add(ticket);
            }
        }});

        // when
        final byte discountPercent = wholesaleDiscountStrategy.getDiscount(user, midEvent, now, 1);

        // then
        assertEquals(50, discountPercent);
    }

    @Test
    public void shouldDependOnTicketCount() {
        // given
        final User user = new User(idGenerator++).setBirthDay(LocalDateTime.now().minusDays(4));

        // when
        final byte discountPercent = wholesaleDiscountStrategy.getDiscount(user, highEvent, now, 50);

        // then
        assertEquals(5, discountPercent);
    }

}
