package ua.epam.spring.hometask.dao.impl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.domain.Event;

/**
 * @author Evgeny_Botyanovsky
 */
public class EventDaoImpl implements EventDao {

    private static Map<Long, Event> events = new HashMap<>();

    public EventDaoImpl(){
    }

    @Override
    public Event save(@Nonnull Event entity) {
        return events.put(entity.getId(), entity) == null ? entity : null;
    }

    @Override
    public void saveMany(@Nonnull Event... entities) {
        events.putAll(Stream.of(entities).collect(Collectors.toMap(Event::getId, Function.identity())));
    }

    @Override
    public Event getById(@Nonnull Long id) {
        return events.get(id);
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return Collections.unmodifiableCollection(events.values());
    }

    @Override
    public Event update(@Nonnull Event entity) {
        return events.containsKey(entity.getId()) ?
                events.put(entity.getId(), entity) :
                null;
    }

    @Override
    public void delete(@Nonnull Event entity) {
        events.remove(entity.getId(), entity);
    }

    @Nonnull
    @Override
    public Collection<Event> getForDateRange(@Nonnull LocalDateTime from, @Nonnull LocalDateTime to) {
        return events.values().stream()
                .filter(event -> event.getAirDates()
                        .stream()
                        .anyMatch(airDate -> airDate.isAfter(from) && airDate.isBefore(to)))
                .collect(Collectors.toSet());
    }

    @Nonnull
    @Override
    public Collection<Event> getNextEvents(@Nonnull LocalDateTime to) {
        return getForDateRange(LocalDateTime.now(), to);
    }

    @Override
    public Event getByName(@Nonnull String name) {
        return events.values().stream()
                .filter(event -> name.equals(event.getName()))
                .findAny()
                .orElse(null);
    }

}
