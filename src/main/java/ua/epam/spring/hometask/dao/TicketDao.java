package ua.epam.spring.hometask.dao;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

/**
 * @author Evgeny_Botyanovsky
 */
public interface TicketDao extends BasicDao<Ticket> {

    /**
     * Get tickets purchased by specified user
     *
     * @param user user
     * @return collections of tickets
     */
    @Nonnull
    Collection<Ticket> getUserTickets(@Nullable User user);

    /**
     * Get tickets purchased for event
     *
     * @param event event
     * @param date  date of event
     * @return collection of tickets
     */
    @Nonnull
    Collection<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime date);

}
