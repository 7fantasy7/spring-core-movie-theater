package ua.epam.spring.hometask.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SortNatural;

import ua.epam.spring.hometask.domain.stats.DiscountStatistics;

/**
 * @author Yuriy_Tkach
 */
@Entity
@Table(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class User extends DomainObject {

    @XmlElement
    @Column(name = "first_name")
    private String firstName;

    @XmlElement
    @Column(name = "last_name")
    private String lastName;

    @XmlElement
    @Column(name = "email")
    private String email;

    @XmlElement
    @Column(name = "birth_day")
    private LocalDateTime birthDay;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private DiscountStatistics discountStatistics = new DiscountStatistics();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderColumn(name = "order_id")
    @SortNatural
    private SortedSet<Ticket> tickets = new TreeSet<>();

    public User() {
    }

    public User(final Long id) {
        super(id);
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public SortedSet<Ticket> getTickets() {
        return tickets;
    }

    public User setTickets(SortedSet<Ticket> tickets) {
        this.tickets = tickets;
        return this;
    }

    public LocalDateTime getBirthDay() {
        return birthDay;
    }

    public User setBirthDay(LocalDateTime birthDay) {
        this.birthDay = birthDay;
        return this;
    }

    public DiscountStatistics getDiscountStatistics() {
        return discountStatistics;
    }

    public User setDiscountStatistics(DiscountStatistics discountStatistics) {
        this.discountStatistics = discountStatistics;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
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
        User other = (User) obj;
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!email.equals(other.email)) {
            return false;
        }
        if (firstName == null) {
            if (other.firstName != null) {
                return false;
            }
        } else if (!firstName.equals(other.firstName)) {
            return false;
        }
        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(other.lastName)) {
            return false;
        }
        return true;
    }

}
