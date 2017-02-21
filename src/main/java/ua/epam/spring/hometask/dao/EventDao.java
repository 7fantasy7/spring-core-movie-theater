package ua.epam.spring.hometask.dao;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.domain.Event;

/**
 * @author Evgeny_Botyanovsky
 */
public interface EventDao extends BasicDao<Event> {

    /**
     * Returns events for specified date range
     *
     * @param from start date
     * @param to   end date
     * @return events in specified date range
     */
    @Nonnull
    Collection<Event> getForDateRange(@Nonnull LocalDateTime from, @Nonnull LocalDateTime to);

    /**
     * Returns events from now till the ‘to’ date
     *
     * @param to end date
     * @return events from now till specified date
     */
    @Nonnull
    Collection<Event> getNextEvents(@Nonnull LocalDateTime to);

    /**
     * Returns event with specified name
     *
     * @param name name of event
     * @return event with specified name
     */
    @Nullable
    Event getByName(@Nonnull String name);

}
