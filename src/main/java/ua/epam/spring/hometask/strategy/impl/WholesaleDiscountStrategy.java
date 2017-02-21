package ua.epam.spring.hometask.strategy.impl;

import java.time.LocalDateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.dao.TicketDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.strategy.DiscountStrategy;

/**
 * Every 10th ticket strategy
 * Gives 50% for every 10th ticket purchased by user.
 * This strategy can also be applied for not-registered users if 10 or more tickets are bought
 *
 * @author Evgeny_Botyanovsky
 */
public class WholesaleDiscountStrategy implements DiscountStrategy {

    private TicketDao ticketDao;

    private WholesaleDiscountStrategy(final TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime date, long numberOfTickets) {
        int discountTickets;
        if (user == null) {
            discountTickets = (int) (numberOfTickets / 10);
        } else {
            final int ticketsBoughtByUserBefore = ticketDao.getUserTickets(user).size();
            discountTickets = (int) (ticketsBoughtByUserBefore + numberOfTickets / 10)
                    - ticketsBoughtByUserBefore / 10;
        }
        return (byte) (50 * discountTickets + 100 * (numberOfTickets - discountTickets)
                / numberOfTickets * 100);
    }

}
