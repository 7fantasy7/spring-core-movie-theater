package ua.epam.spring.hometask.service.impl;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.EventService;

/**
 * @author Evgeny_Botyanovsky
 */
@Service
@Transactional
public class EventServiceImpl implements EventService {

    private EventDao eventDao;

    @Autowired
    EventServiceImpl(final EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Nullable
    @Override
    public Event getByName(@Nonnull final String name) {
        return eventDao.getByName(name);
    }

    @Override
    public Event save(@Nonnull final Event object) {
        if (eventDao.getByName(object.getName()) == null) {
            return eventDao.save(object);
        }
        return null;
    }

    @Override
    public void remove(@Nonnull final Event object) {
        eventDao.delete(object);
    }

    @Override
    public Event getById(@Nonnull final Long id) {
        return eventDao.getById(id);
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return eventDao.getAll();
    }

    @Nonnull
    @Override
    public Collection<Event> getForDateRange(@Nonnull final LocalDateTime from, @Nonnull final LocalDateTime to) {
        return eventDao.getForDateRange(from, to);
    }

    @Nonnull
    @Override
    public Collection<Event> getNextEvents(@Nonnull final LocalDateTime to) {
        return eventDao.getNextEvents(to);
    }

}
