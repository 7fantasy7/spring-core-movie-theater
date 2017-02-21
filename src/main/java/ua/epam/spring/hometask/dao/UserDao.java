package ua.epam.spring.hometask.dao;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.domain.User;

/**
 * @author Evgeny_Botyanovsky
 */
public interface UserDao extends BasicDao<User> {

    /**
     * Get user by email
     *
     * @param email E-mail address
     * @return user with specified email
     * or null
     */
    @Nullable
    User getUserByEmail(@Nonnull String email);

}
