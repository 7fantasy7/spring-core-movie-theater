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

    byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime date, long numberOfTickets);

}
