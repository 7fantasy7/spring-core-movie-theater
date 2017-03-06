package ua.epam.spring.hometask.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import ua.epam.spring.hometask.domain.stats.EventStatistics;

/**
 * @author Yuriy_Tkach
 */
@Entity
@Table(name = "event")
public class Event extends DomainObject {

    @Column(name = "name")
    private String name;

    @ElementCollection
    @CollectionTable(name = "event_date", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "air_date")
    @OrderBy
    private SortedSet<LocalDateTime> airDates = new TreeSet<>();

    @Column(name = "base_price")
    private double basePrice;

    @Enumerated
    private EventRating rating;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "event_auditorium", joinColumns = {@JoinColumn(name = "event_id")}, inverseJoinColumns = {@JoinColumn(name = "auditorium_id")})
    @OrderBy
    @MapKeyColumn(name = "date")
    private SortedMap<LocalDateTime, Auditorium> auditoriums = new TreeMap<>();

    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL)
    private EventStatistics eventStatistics = new EventStatistics(this);

    public Event() {
    }

    public Event(final Long id) {
        super(id);
    }

    /**
     * Checks if event is aired on particular <code>dateTime</code> and assigns
     * auditorium to it.
     *
     * @param dateTime   Date and time of aired event for which to assign
     * @param auditorium Auditorium that should be assigned
     * @return <code>true</code> if successful, <code>false</code> if event is
     * not aired on that date
     */
    public boolean assignAuditorium(LocalDateTime dateTime, Auditorium auditorium) {
        if (airDates.contains(dateTime)) {
            auditoriums.put(dateTime, auditorium);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes auditorium assignment from event
     *
     * @param dateTime Date and time to remove auditorium for
     * @return <code>true</code> if successful, <code>false</code> if not
     * removed
     */
    public boolean removeAuditoriumAssignment(LocalDateTime dateTime) {
        return auditoriums.remove(dateTime) != null;
    }

    /**
     * Add date and time of event air
     *
     * @param dateTime Date and time to add
     * @return <code>true</code> if successful, <code>false</code> if already
     * there
     */
    public boolean addAirDateTime(LocalDateTime dateTime) {
        return airDates.add(dateTime);
    }

    /**
     * Adding date and time of event air and assigning auditorium to that
     *
     * @param dateTime   Date and time to add
     * @param auditorium Auditorium to add if success in date time add
     * @return <code>true</code> if successful, <code>false</code> if already
     * there
     */
    public boolean addAirDateTime(LocalDateTime dateTime, Auditorium auditorium) {
        boolean result = airDates.add(dateTime);
        if (result) {
            auditoriums.put(dateTime, auditorium);
        }
        return result;
    }

    /**
     * Removes the date and time of event air. If auditorium was assigned to
     * that date and time - the assignment is also removed
     *
     * @param dateTime Date and time to remove
     * @return <code>true</code> if successful, <code>false</code> if not there
     */
    public boolean removeAirDateTime(LocalDateTime dateTime) {
        boolean result = airDates.remove(dateTime);
        if (result) {
            auditoriums.remove(dateTime);
        }
        return result;
    }

    /**
     * Checks if event airs on particular date and time
     *
     * @param dateTime Date and time to check
     * @return <code>true</code> event airs on that date and time
     */
    public boolean airsOnDateTime(LocalDateTime dateTime) {
        return airDates.stream().anyMatch(dt -> dt.equals(dateTime));
    }

    /**
     * Checks if event airs on particular date
     *
     * @param date Date to ckeck
     * @return <code>true</code> event airs on that date
     */
    public boolean airsOnDate(LocalDate date) {
        return airDates.stream().anyMatch(dt -> dt.toLocalDate().equals(date));
    }

    /**
     * Checking if event airs on dates between <code>from</code> and
     * <code>to</code> inclusive
     *
     * @param from Start date to check
     * @param to   End date to check
     * @return <code>true</code> event airs on dates
     */
    public boolean airsOnDates(LocalDate from, LocalDate to) {
        return airDates.stream()
                .anyMatch(dt -> dt.toLocalDate().compareTo(from) >= 0 && dt.toLocalDate().compareTo(to) <= 0);
    }

    public String getName() {
        return name;
    }

    public Event setName(String name) {
        this.name = name;
        return this;
    }

    public SortedSet<LocalDateTime> getAirDates() {
        return airDates;
    }

    public Event setAirDates(SortedSet<LocalDateTime> airDates) {
        this.airDates = airDates;
        return this;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public Event setBasePrice(double basePrice) {
        this.basePrice = basePrice;
        return this;
    }

    public EventRating getRating() {
        return rating;
    }

    public Event setRating(EventRating rating) {
        this.rating = rating;
        return this;
    }

    public SortedMap<LocalDateTime, Auditorium> getAuditoriums() {
        return auditoriums;
    }

    public Event setAuditoriums(SortedMap<LocalDateTime, Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
        return this;
    }

    public EventStatistics getEventStatistics() {
        return eventStatistics;
    }

    public Event setEventStatistics(EventStatistics eventStatistics) {
        this.eventStatistics = eventStatistics;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
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
        Event other = (Event) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
