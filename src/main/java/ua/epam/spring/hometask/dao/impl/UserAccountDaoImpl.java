package ua.epam.spring.hometask.dao.impl;

import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.UserAccountDao;
import ua.epam.spring.hometask.domain.UserAccount;

/*
  1. Add DAO and service objects for new entity
 */
@Repository
public class UserAccountDaoImpl extends BasicDaoImpl<UserAccount> implements UserAccountDao {

    @Override
    public void withdrawMoney(long userId, double amount) {
        getCurrentSession()
                .createNativeQuery("update user_account set money = money - :amount where user_id = :userId")
                .setParameter("amount", amount)
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Override
    public void depositMoney(long userId, double amount) {
        getCurrentSession()
                .createNativeQuery("update user_account set money = money + :amount where user_id = :userId")
                .setParameter("amount", amount)
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Override
    protected Class<UserAccount> getEntityClass() {
        return UserAccount.class;
    }

}
