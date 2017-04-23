package ua.epam.spring.hometask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.dao.UserAccountDao;
import ua.epam.spring.hometask.service.UserAccountService;

/*
  1. Add DAO and service objects for new entity
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountDao userAccountDao;

    @Autowired
    public UserAccountServiceImpl(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }

    @Override
    public void withdrawMoney(long userId, double amount) {
        userAccountDao.withdrawMoney(userId, amount);
    }

    @Override
    public void depositMoney(long userId, double amount) {
        userAccountDao.depositMoney(userId, amount);
    }

}
