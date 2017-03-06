package ua.epam.spring.hometask.strategy.impl;

import java.time.LocalDateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Column;

import org.springframework.stereotype.Component;

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
@Component
public class WholesaleDiscountStrategy implements DiscountStrategy {

    private TicketDao ticketDao;

    public WholesaleDiscountStrategy(final TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime date, long numberOfTickets) {
        int discountTickets;
        if (user == null) {
            discountTickets = (int) (numberOfTickets / 10);
        } else {
            final int ticketsBoughtByUserAfterLastDiscount = ticketDao.getUserTickets(user).size() % 10;
            discountTickets = (int) (ticketsBoughtByUserAfterLastDiscount + numberOfTickets) / 10;
        }
        final double coeff = Math.floor((50 * discountTickets) + (100 * (numberOfTickets - discountTickets))
                / (numberOfTickets * 100));
        final double tempDiscount = coeff == 1 ? 0 : coeff / numberOfTickets;
        return (byte) tempDiscount;
    }

}
