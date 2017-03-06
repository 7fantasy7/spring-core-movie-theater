package ua.epam.spring.hometask.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * @author Yuriy_Tkach
 */
@Entity
@Table(name = "auditorium")
public class Auditorium extends DomainObject {

    @Column(name = "name")
    private String name;

    @Column(name = "number_of_seats")
    private long numberOfSeats;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "auditorium_vip_seat", joinColumns = @JoinColumn(name = "auditorium_id"))
    private Set<Long> vipSeats = Collections.emptySet();

    public Auditorium() {
    }

    public Auditorium(Long id) {
        super(id);
    }

    /**
     * Counts how many vip seats are there in supplied <code>seats</code>
     *
     * @param seats Seats to process
     * @return number of vip seats in request
     */
    public long countVipSeats(Collection<Long> seats) {
        return seats.stream().filter(seat -> vipSeats.contains(seat)).count();
    }

    public String getName() {
        return name;
    }

    public Auditorium setName(String name) {
        this.name = name;
        return this;
    }

    public long getNumberOfSeats() {
        return numberOfSeats;
    }

    public Auditorium setNumberOfSeats(long numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
        return this;
    }

    public Set<Long> getAllSeats() {
        return LongStream.range(1, numberOfSeats + 1).boxed().collect(Collectors.toSet());
    }

    public Set<Long> getVipSeats() {
        return vipSeats;
    }

    public Auditorium setVipSeats(Set<Long> vipSeats) {
        this.vipSeats = vipSeats;
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
        Auditorium other = (Auditorium) obj;
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
