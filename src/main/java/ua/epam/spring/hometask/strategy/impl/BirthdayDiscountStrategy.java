package ua.epam.spring.hometask.strategy.impl;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.stereotype.Component;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.strategy.DiscountStrategy;

/**
 * Birthday strategy
 * Gives 5%, if user has birthday within 5 days of air date
 *
 * @author Evgeny_Botyanovsky
 */
@Component
public class BirthdayDiscountStrategy implements DiscountStrategy {

    public BirthdayDiscountStrategy(){
    }

    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {
        if (user != null) {
            final LocalDateTime userBirthDay = user.getBirthDay();
            final List<LocalDateTime> localDateTimes = event.getAirDates().stream()
                    .filter(airDate -> airDate.isBefore(userBirthDay)).collect(Collectors.toList());
            final LocalDateTime eventClosestFloorAir = localDateTimes.size() > 1 ? localDateTimes.get(localDateTimes.size()-1) : null;
            final LocalDateTime eventClosestCeilingAir = event.getAirDates().stream()
                    .filter(airDate -> airDate.isAfter(userBirthDay)).findFirst().orElse(null);
            if ((eventClosestFloorAir != null && DAYS.between(userBirthDay, eventClosestFloorAir) < 5) ||
                    (eventClosestCeilingAir != null && DAYS.between(userBirthDay, eventClosestCeilingAir) < 5)) {
                return 5;
            }
        }
        return 0;
    }

}
