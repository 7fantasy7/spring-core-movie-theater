package ua.epam.spring.hometask.service;

public interface UserAccountService {

    void withdrawMoney(long userId, double amount);

    void depositMoney(long userId, double amount);
}
