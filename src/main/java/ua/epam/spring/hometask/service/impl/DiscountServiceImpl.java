package ua.epam.spring.hometask.service.impl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.strategy.DiscountStrategy;

/**
 * @author Evgeny_Botyanovsky
 */
@Service
@Transactional
public class DiscountServiceImpl implements DiscountService {

    private Collection<DiscountStrategy> discountStrategies;

    @Autowired
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
