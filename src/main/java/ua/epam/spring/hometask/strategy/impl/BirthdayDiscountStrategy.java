package ua.epam.spring.hometask.strategy.impl;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.strategy.DiscountStrategy;

/**
 * Birthday strategy
 * Gives 5%, if user has birthday within 5 days of air date
 *
 * @author Evgeny_Botyanovsky
 */
public class BirthdayDiscountStrategy implements DiscountStrategy {

    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {
        if (user != null) {
            final LocalDateTime userBirthDay = user.getBirthDay();
            final LocalDateTime eventClosestAir = event.getAirDates().floor(userBirthDay);
            if (DAYS.between(userBirthDay, eventClosestAir) < 5) {
                return 5;
            }
        }
        return 0;
    }

}