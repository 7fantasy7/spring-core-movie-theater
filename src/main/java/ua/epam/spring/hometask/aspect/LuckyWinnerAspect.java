package ua.epam.spring.hometask.aspect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import ua.epam.spring.hometask.domain.Ticket;

/**
 * @author Evgeny_Botyanovsky
 */
@Component
@Aspect
public class LuckyWinnerAspect {

    private Collection<String> luckyTickets = new ArrayList<>();
    private Random luckyRandom = new Random();

    @Pointcut("execution(public void ua.epam.spring.hometask.service.BookingService.bookTickets(..))")
    public void bookTicketPointCut() {
    }

    @Around("bookTicketPointCut()")
    public void luckyEvent(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("Lucky Winner Aspect: luckyEvent");
        proceedingJoinPoint.proceed();
        if (luckyRandom.nextBoolean()) {
            ((Set<Ticket>) proceedingJoinPoint.getArgs()[0]).forEach(ticket -> ticket.setPrice(0));
            luckyTickets.add("Time: " + System.nanoTime() + ", Tickets: " + Arrays.toString(proceedingJoinPoint.getArgs()));
        }
    }

    public Collection<String> getLuckyTickets() {
        return Collections.unmodifiableCollection(luckyTickets);
    }

}
