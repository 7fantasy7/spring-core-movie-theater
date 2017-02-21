package ua.epam.spring.hometask.dao.impl;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import ua.epam.spring.hometask.dao.TicketDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

/**
 * @author Evgeny_Botyanovsky
 */
public class TicketDaoImpl implements TicketDao {

    private static final Map<Long, Ticket> tickets  = new HashMap<>();

    @Override
    public Ticket save(@Nonnull Ticket entity) {
        return tickets.put(entity.getId(), entity) == null ? entity : null;
    }

    @Override
    public void saveMany(@Nonnull Ticket... entities) {
        tickets.putAll(Stream.of(entities).collect(Collectors.toMap(Ticket::getId, Function.identity())));
    }

    @Override
    public Ticket getById(@Nonnull Serializable id) {
        return tickets.get(id);
    }

    @Nonnull
    @Override
    public Collection<Ticket> getAll() {
        return Collections.unmodifiableCollection(tickets.values());
    }

    @Override
    public Ticket update(@Nonnull Ticket entity) {
        return tickets.containsKey(entity.getId()) ?
                tickets.put(entity.getId(), entity) :
                null;
    }

    @Override
    public void delete(@Nonnull Ticket entity) {
        tickets.remove(entity.getId(), entity);
    }

    @Nonnull
    @Override
    public Collection<Ticket> getUserTickets(User user) {
        return tickets.values().stream()
                .filter(ticket -> user.equals(ticket.getUser()))
                .collect(Collectors.toList());
    }

    @Nonnull
    @Override
    public Collection<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime date) {
        return tickets.values().stream()
                .filter(ticket -> event.equals(ticket.getEvent()))
                .filter(ticket -> date.equals(ticket.getDateTime()))
                .collect(Collectors.toList());
    }

}
