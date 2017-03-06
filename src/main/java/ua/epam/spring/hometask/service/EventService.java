package ua.epam.spring.hometask.service;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.domain.Event;

/**
 * @author Yuriy_Tkach
 */
public interface EventService extends AbstractDomainObjectService<Event> {

    @Override
    Event save(@Nonnull Event object);
    /**
     * Finding event by name
     *
     * @param name Name of the event
     * @return found event or <code>null</code>
     */
    @Nullable
    Event getByName(@Nonnull String name);

    /**
     * Finding all events that air on specified date range
     *
     * @param from Start date
     * @param to   End date inclusive
     * @return Set of events
     */
    @Nonnull
    Collection<Event> getForDateRange(@Nonnull LocalDateTime from, @Nonnull LocalDateTime to);

    /**
     * Return events from 'now' till the the specified date time
     *
     * @param to End date time inclusive
     * @return Set of events
     */
    @Nonnull
    Collection<Event> getNextEvents(@Nonnull LocalDateTime to);

}
