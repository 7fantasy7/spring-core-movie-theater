package ua.epam.spring.hometask.service.impl;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;

/**
 * @author Evgeny_Botyanovsky
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    UserServiceImpl(final UserDao userDao) {
        this.userDao = userDao;
    }

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull final String email) {
        return userDao.getUserByEmail(email);
    }

    @Nonnull
    @Override
    public User save(@Nonnull final User object) {
        return userDao.save(object);
    }

    @Override
    public void remove(@Nonnull final User object) {
        userDao.delete(object);
    }

    @Nullable
    @Override
    public User getById(@Nonnull final Long id) {
        return userDao.getById(id);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return userDao.getAll();
    }

}
