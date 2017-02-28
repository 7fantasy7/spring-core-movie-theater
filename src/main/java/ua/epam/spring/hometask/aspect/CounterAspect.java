package ua.epam.spring.hometask.aspect;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;

/**
 * @author Evgeny_Botyanovsky
 */
@Component
@Aspect
public class CounterAspect {

    @Autowired
    private EventDao eventDao;

    @Pointcut("execution(* ua.epam.spring.hometask.service.EventService.getByName(..))")
    public void getEventByNamePointCut() {
    }

    @Pointcut("execution(* ua.epam.spring.hometask.service.BookingService.getTicketsPrice(..))")
    public void getTicketPricePointCut() {
    }

    @Pointcut("execution(* ua.epam.spring.hometask.service.BookingService.bookTickets(..))")
    public void bookTicketPointCut() {
    }

    @Before("getEventByNamePointCut()")
    public void updateAccessedByNameCount(final JoinPoint joinPoint) {
        final String eventName = (String) joinPoint.getArgs()[0];
        final Event event = eventDao.getByName(eventName);
        if (Objects.isNull(event)) {
            return;
        }
        System.out.println("Counter Aspect: updateAccessedByNameCount");
        event.getEventStatistics().incAccessedByName();
        eventDao.update(event);
    }

    @Before("getTicketPricePointCut()")
    public void updateGetTicketPriceCount(final JoinPoint joinPoint) {
        final Event event = (Event) joinPoint.getArgs()[0];
        if (Objects.isNull(event)) {
            return;
        }
        System.out.println("Counter Aspect: updateGetTicketPriceCount");
        event.getEventStatistics().incPricesQueried();
        eventDao.update(event);
    }

    @Before("bookTicketPointCut()")
    public void updateBookTicketCount(final JoinPoint joinPoint) {
        final Set<Ticket> tickets = (Set<Ticket>) joinPoint.getArgs()[0];
        System.out.println("Counter Aspect: updateBookTicketCount");
        tickets.forEach(this::updateTicket);
    }

    private void updateTicket(final Ticket ticket) {
        final Event event = ticket.getEvent();
        event.getEventStatistics().incBookedTickets();
        eventDao.update(event);
    }

}
