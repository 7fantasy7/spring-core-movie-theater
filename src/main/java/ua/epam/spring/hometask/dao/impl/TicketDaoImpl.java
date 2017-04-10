package ua.epam.spring.hometask.dao.impl;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.annotation.Nonnull;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ua.epam.spring.hometask.dao.TicketDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

/**
 * @author Evgeny_Botyanovsky
 */
@Repository
public class TicketDaoImpl extends BasicDaoImpl<Ticket>implements TicketDao {

    public TicketDaoImpl() {
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public Collection<Ticket> getUserTickets(User user) {
        return (Collection<Ticket>) getCurrentSession().createCriteria(getEntityClass())
                .add(Restrictions.eq("user.id", user.getId()))
                .list();
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public Collection<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime date) {
        return getCurrentSession().createCriteria(getEntityClass())
                .add(Restrictions.eq("event.id", event.getId()))
                .add(Restrictions.in("airDates", date))
                .list();
    }

    @Override
    protected Class<Ticket> getEntityClass() {
        return Ticket.class;
    }
}
