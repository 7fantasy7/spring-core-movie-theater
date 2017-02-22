package ua.epam.spring.hometask.service.impl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.dao.TicketDao;
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.DiscountService;

/**
 * @author Evgeny_Botyanovsky
 */
public class BookingServiceImpl implements BookingService {

    private static final double VIP_SEATS_MULTIPLIER = 2;
    private static final double HIGH_RATED_MOVIES_MULTIPLIER = 1.2;

    private DiscountService discountService;

    private TicketDao ticketDao;

    BookingServiceImpl(final DiscountService discountService, final TicketDao ticketDao) {
        this.discountService = discountService;
        this.ticketDao = ticketDao;
    }

    @Override
    public double getTicketsPrice(@Nonnull final Event event, @Nonnull final LocalDateTime dateTime,
                                  @Nullable final User user, @Nonnull final Set<Long> seats) {
        final byte discountPercentage = discountService.getDiscount(user, event, dateTime, seats.size());
        final double basePrice = event.getBasePrice();
        final Auditorium auditorium = event.getAuditoriums().get(dateTime);

        final long vipSeatsCount = countVipSeats(auditorium, seats);
        final long commonSeatsCount = seats.size() - vipSeatsCount;

        return countResultTicketsPrice(event, basePrice, discountPercentage,
                commonSeatsCount, vipSeatsCount);
    }

    @Override
    public void bookTickets(@Nonnull final Set<Ticket> tickets) {
        final User user = tickets.iterator().next().getUser();
        final Ticket[] ticketArray = new Ticket[tickets.size()];
        tickets.toArray(ticketArray);

        ticketDao.saveMany(ticketArray);
        if (user != null) {
            user.getTickets().addAll(tickets);
        }
    }

    @Nonnull
    @Override
    public Collection<Ticket> getPurchasedTicketsForEvent(@Nonnull final Event event, @Nonnull final LocalDateTime dateTime) {
        return ticketDao.getPurchasedTicketsForEvent(event, dateTime);
    }

    private long countVipSeats(final Auditorium auditorium, final Collection<Long> seats) {
        return seats.stream()
                .filter(seat -> auditorium.getVipSeats().contains(seat))
                .count();
    }

    private double countResultTicketsPrice(final Event event, final double basePrice, final double discountPercentage,
                                           final long commonSeatsCount, final long vipSeatsCount) {
        double resultPrice = commonSeatsCount * basePrice + vipSeatsCount * basePrice * VIP_SEATS_MULTIPLIER;
        if (event.getRating() == EventRating.HIGH) {
            resultPrice *= HIGH_RATED_MOVIES_MULTIPLIER;
        }
        resultPrice = resultPrice - discountPercentage * resultPrice;
        return resultPrice;
    }

}
