package ua.epam.spring.hometask.dao.impl;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.annotation.Nonnull;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.domain.Event;

/**
 * @author Evgeny_Botyanovsky
 */
@Repository
public class EventDaoImpl extends BasicDaoImpl<Event> implements EventDao {

    public EventDaoImpl() {
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public Collection<Event> getForDateRange(@Nonnull LocalDateTime from, @Nonnull LocalDateTime to) {
        return (Collection<Event>) getCurrentSession().createCriteria(getEntityClass())
                .add(Restrictions.and(
                        Restrictions.ge("airDate", from),
                        Restrictions.le("airDate", to)
                )).list();
    }

    @Nonnull
    @Override
    public Collection<Event> getNextEvents(@Nonnull LocalDateTime to) {
        return getForDateRange(LocalDateTime.now(), to);
    }

    @Override
    public Event getByName(@Nonnull String name) {
        return (Event) getCurrentSession().createCriteria(getEntityClass())
                .add(Restrictions.eq("name", name))
                .uniqueResult();
    }

    @Override
    protected Class<Event> getEntityClass() {
        return Event.class;
    }

}
