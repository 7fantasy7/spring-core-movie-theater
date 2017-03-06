package ua.epam.spring.hometask.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import ua.epam.spring.hometask.dao.DiscountDao;
import ua.epam.spring.hometask.dao.util.HibernateUtil;

/**
 * @author Evgeny_Botyanovsky
 */
@Repository
public class DiscountDaoImpl implements DiscountDao {

    @Override
    public long getBirthdayDiscountTimesTotal() {
        return (Long) getCurrentSession().createNativeQuery("SELECT SUM(birthday_discounts) FROM discount_statistics")
                .uniqueResult();
    }

    @Override
    public long getWholesaleDiscountTimesTotal() {
        return (Long) getCurrentSession().createNativeQuery("SELECT SUM(wholesale_discounts) FROM discount_statistics")
                .uniqueResult();
    }

    private Session getCurrentSession() {
        return HibernateUtil.getCurrentSession();
    }

}
