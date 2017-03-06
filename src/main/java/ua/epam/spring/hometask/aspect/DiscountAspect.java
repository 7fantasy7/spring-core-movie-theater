package ua.epam.spring.hometask.aspect;

import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.epam.spring.hometask.dao.DiscountDao;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.User;


/**
 * @author Evgeny_Botyanovsky
 */
@Component
@Aspect
public class DiscountAspect {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DiscountDao discountDao;

    @Pointcut("execution(public * ua.epam.spring.hometask.strategy.impl.BirthdayDiscountStrategy.getDiscount(..))")
    public void birthdayDiscountStrategy() {
    }

    @Pointcut("execution(public * ua.epam.spring.hometask.strategy.impl.WholesaleDiscountStrategy.getDiscount(..))")
    public void wholesaleDiscountStrategy() {
    }

    @Before("birthdayDiscountStrategy()")
    public void updateBirthdayDiscountStrategyStatistics(final JoinPoint joinPoint) throws Throwable {
        final User user = (User) joinPoint.getArgs()[0];
        System.out.println("Discount Aspect: updateBirthdayDiscountStrategyStatistics");
        if (Objects.isNull(user)) {
            System.out.println("Discount Aspect: no birthday discount for unregistered user");
            return;
        }
        user.getDiscountStatistics().incBirthdayDiscounts();
        userDao.update(user);
    }

    @Before("wholesaleDiscountStrategy()")
    public void updateWholesaleDiscountStrategyStatistics(final JoinPoint joinPoint) throws Throwable {
        final User user = (User) joinPoint.getArgs()[0];
        System.out.println("Discount Aspect: updateWholesaleDiscountStrategyStatistics");
        if (Objects.isNull(user)) {
            return;
        }
        user.getDiscountStatistics().incWholesaleDiscounts();
        userDao.update(user);
    }

    public long getBirthdayDiscountTimesTotal() {
        return discountDao.getBirthdayDiscountTimesTotal();
    }

    public long getWholesaleDiscountTimesTotal() {
        return discountDao.getWholesaleDiscountTimesTotal();
    }

    public long getBirthdayDiscountTimesForUser(final User user) {
        return user.getDiscountStatistics().getBirthdayDiscounts();
    }

    public long getWholesaleDiscountTimesForUser(final User user) {
        return user.getDiscountStatistics().getWholesaleDiscounts();
    }

}
