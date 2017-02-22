package ua.epam.spring.hometask.dao.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.User;

/**
 * @author Evgeny_Botyanovsky
 */
public class UserDaoImpl implements UserDao {

    private static Map<Long, User> users = new HashMap<>();

    public UserDaoImpl() {
    }

    @Override
    public User save(@Nonnull User entity) {
        return users.put(entity.getId(), entity) == null ? entity : null;
    }

    @Override
    public void saveMany(@Nonnull User... entities) {
        users.putAll(Stream.of(entities).collect(Collectors.toMap(User::getId, Function.identity())));
    }

    @Override
    public User getById(@Nonnull Long id) {
        return users.get(id);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return Collections.unmodifiableCollection(users.values());
    }

    @Override
    public User update(@Nonnull User entity) {
        return users.containsKey(entity.getId()) ?
                users.put(entity.getId(), entity) :
                null;
    }

    @Override
    public void delete(@Nonnull User entity) {
        users.remove(entity.getId(), entity);
    }

    @Override
    public User getUserByEmail(@Nonnull String email) {
        return users.values().stream()
                .filter(user -> email.equals(user.getEmail()))
                .findAny()
                .orElse(null);
    }
}
