package ua.epam.spring.hometask.domain;

import ua.epam.spring.hometask.domain.jaxb.LocalDateTimeAdapter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "ticket")
@XmlRootElement
@XmlType
public class Ticket extends DomainObject implements Comparable<Ticket> {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "seat")
    private long seat;

    @Column(name = "price")
    private double price;

    public Ticket() {
    }

    public Ticket(User user, Event event, LocalDateTime dateTime, long seat) {
        this.user = user;
        this.event = event;
        this.dateTime = dateTime;
        this.seat = seat;
    }

    public User getUser() {
        return user;
    }

    public Ticket setUser(User user) {
        this.user = user;
        return this;
    }

    public Event getEvent() {
        return event;
    }

    public Ticket setEvent(Event event) {
        this.event = event;
        return this;
    }

    @XmlJavaTypeAdapter(type = LocalDateTime.class, value = LocalDateTimeAdapter.class)
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Ticket setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public long getSeat() {
        return seat;
    }

    public Ticket setSeat(long seat) {
        this.seat = seat;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Ticket setPrice(double price) {
        this.price = price;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, event, seat);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Ticket other = (Ticket) obj;
        if (dateTime == null) {
            if (other.dateTime != null) {
                return false;
            }
        } else if (!dateTime.equals(other.dateTime)) {
            return false;
        }
        if (event == null) {
            if (other.event != null) {
                return false;
            }
        } else if (!event.equals(other.event)) {
            return false;
        }
        if (seat != other.seat) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Ticket other) {
        if (other == null) {
            return 1;
        }
        int result = dateTime.compareTo(other.getDateTime());

        if (result == 0) {
            result = event.getName().compareTo(other.getEvent().getName());
        }
        if (result == 0) {
            result = Long.compare(seat, other.getSeat());
        }
        return result;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "user=" + user +
                ", event=" + event +
                ", dateTime=" + dateTime +
                ", seat=" + seat +
                ", price=" + price +
                '}';
    }
}
