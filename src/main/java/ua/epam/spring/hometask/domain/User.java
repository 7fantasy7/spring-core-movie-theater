package ua.epam.spring.hometask.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SortNatural;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.epam.spring.hometask.domain.jaxb.LocalDateTimeAdapter;
import ua.epam.spring.hometask.domain.stats.DiscountStatistics;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@XmlType
public class User extends DomainObject implements UserDetails {

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
    @Column(name = "password")
    @JsonIgnore
    private String password;

    @XmlElement
    @Column(name = "roles")
    private String roles;

    @XmlElement
    @XmlJavaTypeAdapter(type = LocalDateTime.class, value = LocalDateTimeAdapter.class)
    @Column(name = "birth_day")
    private LocalDateTime birthDay;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private DiscountStatistics discountStatistics = new DiscountStatistics();

    @XmlElementWrapper(name = "tickets")
    @XmlElement(name = "ticket")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderColumn(name = "order_id")
    @SortNatural
    private SortedSet<Ticket> tickets = new TreeSet<>();

    @XmlElement
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private UserAccount userAccount = new UserAccount();

    @XmlTransient
    @Transient
    @JsonIgnore
    private List<SimpleGrantedAuthority> authorities = new ArrayList<>();

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

    public String getPassword() {
        return password;
    }

    public User setAuthorities(List<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRoles() {
        return roles;
    }

    public User setRoles(String roles) {
        this.roles = roles;
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

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public User setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        return this;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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
