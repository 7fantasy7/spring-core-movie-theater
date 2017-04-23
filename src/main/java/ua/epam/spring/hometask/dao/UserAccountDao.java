package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.UserAccount;

public interface UserAccountDao extends BasicDao<UserAccount> {

    void withdrawMoney(long userId, double amount);

    void depositMoney(long userId, double amount);

}
