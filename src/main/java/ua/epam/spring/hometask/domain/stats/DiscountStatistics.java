package ua.epam.spring.hometask.domain.stats;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import ua.epam.spring.hometask.domain.DomainObject;
import ua.epam.spring.hometask.domain.User;

/**
 * @author Evgeny_Botyanovsky
 */
@Entity
@Table(name = "discount_statistics")
public class DiscountStatistics extends DomainObject {

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private User user;

    @Column(name = "birthday_discounts")
    private long birthdayDiscounts = 0L;

    @Column(name = "wholesale_discounts")
    private long wholesaleDiscounts = 0L;

    public DiscountStatistics() {
    }

    public long incBirthdayDiscounts() {
        return birthdayDiscounts++;
    }

    public long incWholesaleDiscounts() {
        return wholesaleDiscounts++;
    }

    public User getUser() {
        return user;
    }

    public DiscountStatistics setUser(User user) {
        this.user = user;
        return this;
    }

    public long getBirthdayDiscounts() {
        return birthdayDiscounts;
    }

    public DiscountStatistics setBirthdayDiscounts(long birthdayDiscounts) {
        this.birthdayDiscounts = birthdayDiscounts;
        return this;
    }

    public long getWholesaleDiscounts() {
        return wholesaleDiscounts;
    }

    public DiscountStatistics setWholesaleDiscounts(long wholesaleDiscounts) {
        this.wholesaleDiscounts = wholesaleDiscounts;
        return this;
    }

    @Override
    public String toString() {
        return "DiscountTimes{" +
                "birthdayDiscounts=" + birthdayDiscounts +
                ", wholesaleDiscounts=" + wholesaleDiscounts +
                '}';
    }
}