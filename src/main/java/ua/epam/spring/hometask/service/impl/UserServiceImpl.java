package ua.epam.spring.hometask.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;

/**
 * @author Evgeny_Botyanovsky
 */
public class UserServiceImpl implements UserService {

    private final static Collection<User> users;

    static {
        users = new HashSet<>();
    }

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        return users.stream()
                .filter(user -> email.equals(user.getEmail()))
                .findAny()
                .orElse(null);
    }

    @Nonnull
    @Override
    public User save(@Nonnull User object) {
        return users.add(object) ? object : null;
    }

    @Override
    public void remove(@Nonnull User object) {
        users.remove(object);
    }

    @Nullable
    @Override
    public User getById(@Nonnull Long id) {
        return users.stream()
                .filter(user -> id.equals(user.getId()))
                .findAny()
                .orElse(null);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return Collections.unmodifiableCollection(users);
    }

}
