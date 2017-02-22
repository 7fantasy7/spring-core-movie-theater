package ua.epam.spring.hometask.strategy;

import java.time.LocalDateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

/**
 * @author Evgeny_Botyanovsky
 */
public interface DiscountStrategy {

    /**
     * Count discount for specific order
     *
     * @param user            user, who makes order
     * @param event           event, with user wants to visit
     * @param date            date of event
     * @param numberOfTickets number of tickets
     * @return discount value in percents
     */
    byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime date, long numberOfTickets);

}
