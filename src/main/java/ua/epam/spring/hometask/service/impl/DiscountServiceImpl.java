package ua.epam.spring.hometask.service.impl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.strategy.DiscountStrategy;

/**
 * @author Evgeny_Botyanovsky
 */
public class DiscountServiceImpl implements DiscountService {

    private Collection<DiscountStrategy> discountStrategies;

    DiscountServiceImpl(final Collection<DiscountStrategy> discountStrategies) {
        this.discountStrategies = discountStrategies;
    }

    @Override
    public byte getDiscount(@Nullable final User user, @Nonnull final Event event,
                            @Nonnull final LocalDateTime airDateTime, final long numberOfTickets) {
        return discountStrategies.stream()
                .map(discountStrategy -> discountStrategy.getDiscount(user, event, airDateTime, numberOfTickets))
                .max(Byte::compareTo).orElse((byte) 0);
    }

}
