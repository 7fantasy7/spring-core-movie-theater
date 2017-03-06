package ua.epam.spring.hometask.domain.stats;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import ua.epam.spring.hometask.domain.DomainObject;
import ua.epam.spring.hometask.domain.Event;

/**
 * @author Evgeny_Botyanovsky
 */
@Entity
@Table(name = "event_statistics")
public class EventStatistics extends DomainObject {

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Event event;

    @Column(name = "accessed_by_name")
    private Long accessedByName = 0L;

    @Column(name = "prices_queried")
    private Long pricesQueried = 0L;

    @Column(name = "booked_tickets")
    private Long bookedTickets = 0L;

    public EventStatistics() {
    }

    public EventStatistics(Event event) {
        this.event = event;
    }

    public void incAccessedByName() {
        accessedByName++;
    }

    public void incPricesQueried() {
        pricesQueried++;
    }

    public void incBookedTickets() {
        bookedTickets++;
    }

    public Event getEvent() {
        return event;
    }

    public EventStatistics setEvent(Event event) {
        this.event = event;
        return this;
    }

    public Long getAccessedByName() {
        return accessedByName;
    }

    public EventStatistics setAccessedByName(Long accessedByName) {
        this.accessedByName = accessedByName;
        return this;
    }

    public Long getPricesQueried() {
        return pricesQueried;
    }

    public EventStatistics setPricesQueried(Long pricesQueried) {
        this.pricesQueried = pricesQueried;
        return this;
    }

    public Long getBookedTickets() {
        return bookedTickets;
    }

    public EventStatistics setBookedTickets(Long bookedTickets) {
        this.bookedTickets = bookedTickets;
        return this;
    }

    @Override
    public String toString() {
        return "EventStatistics{" +
                "accessedByName=" + accessedByName +
                ", pricesQueried=" + pricesQueried +
                ", bookedTickets=" + bookedTickets +
                '}';
    }
}