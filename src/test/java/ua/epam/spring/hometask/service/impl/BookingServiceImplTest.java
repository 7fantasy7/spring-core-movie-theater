package ua.epam.spring.hometask.service.impl;

import static com.google.common.collect.Sets.newHashSet;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

import javax.annotation.Nonnull;

import org.junit.Before;
import org.junit.Test;

import ua.epam.spring.hometask.dao.TicketDao;
import ua.epam.spring.hometask.dao.impl.TicketDaoImpl;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.strategy.impl.BirthdayDiscountStrategy;
import ua.epam.spring.hometask.strategy.impl.WholesaleDiscountStrategy;

/**
 * @author Evgeny_Botyanovsky
 */
public class BookingServiceImplTest {

    private BookingServiceImpl bookingService;

    private TicketDao ticketDao;

    private long idGenerator = 1;

    @Before
    public void setUp() {
        final BirthdayDiscountStrategy birthdayDiscountStrategy = new BirthdayDiscountStrategy();
        final WholesaleDiscountStrategy wholesaleDiscountStrategy = new WholesaleDiscountStrategy(ticketDao);
        final DiscountService discountService = new DiscountServiceImpl(newHashSet(birthdayDiscountStrategy, wholesaleDiscountStrategy));
        ticketDao = new TicketDaoImpl();
        bookingService = new BookingServiceImpl(discountService, ticketDao);
    }

    @Test
    public void shouldAddBookedTickets() {
        // given
        final User user = new User(10L);
        final Ticket ticketOne = new Ticket(user, new Event().setName("one"), LocalDateTime.now(), 1);
        ticketOne.setId(idGenerator++);
        final Ticket ticketTwo = new Ticket(user, new Event().setName("two"), LocalDateTime.now(), 2);
        ticketTwo.setId(idGenerator++);
        final Ticket ticketThree = new Ticket(user, new Event().setName("three"), LocalDateTime.now(), 3);
        ticketThree.setId(idGenerator++);

        // when
        bookingService.bookTickets(newHashSet(ticketOne, ticketTwo, ticketThree));

        // then
        final Collection<Ticket> userTickets = ticketDao.getUserTickets(user);
        assertEquals(newHashSet(userTickets), newHashSet(ticketOne, ticketTwo, ticketThree));
    }



}
