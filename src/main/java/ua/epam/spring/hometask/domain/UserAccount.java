package ua.epam.spring.hometask.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "user_account")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class UserAccount {

    @Id
    @GenericGenerator(name = "generator", strategy = "foreign",
            parameters = @Parameter(name = "property", value = "user"))
    @GeneratedValue(generator = "generator")
    @Column(name = "user_id")
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;

    @XmlElement
    @Column(name = "money")
    private double money;

    public User getUser() {
        return user;
    }

    public UserAccount setUser(User user) {
        this.user = user;
        return this;
    }

    public double getMoney() {
        return money;
    }

    public UserAccount setMoney(double money) {
        this.money = money;
        return this;
    }
}
