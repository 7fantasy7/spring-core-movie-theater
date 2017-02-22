package ua.epam.spring.hometask.dao.impl;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ua.epam.spring.hometask.domain.User;

/**
 * @author Evgeny_Botyanovsky
 */
public class UserDaoImplTest {

    private UserDaoImpl userDao;

    private static Map<Long, User> users;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        userDao = new UserDaoImpl();
        final Field usersField = UserDaoImpl.class.getDeclaredField("users");
        usersField.setAccessible(true);

        users = new HashMap<Long, User>() {{
            put(1L, new User(1L).setFirstName("user1First").setLastName("user1Last").setEmail("user1@epam.ua"));
            put(2L, new User(2L).setFirstName("user2First").setLastName("user2Last").setEmail("user2@epam.ua"));
            put(3L, new User(3L).setFirstName("user3First").setLastName("user3Last").setEmail("user3@epam.ua"));
        }};
        usersField.set(userDao, users);
    }

    @Test
    public void shouldReturnById() {
        // when
        final User userById = userDao.getById(1L);

        // then
        assertEquals(users.get(1L), userById);
    }

    @Test
    public void shouldReturnByEmail() {
        // when
        final User userByEmail = userDao.getUserByEmail("user2@epam.ua");

        // then
        assertEquals(users.get(2L), userByEmail);
    }

    @Test
    public void shouldReturnNullByNotExistingName() {
        // when
        final User userByNotExistingEmail = userDao.getUserByEmail("Not existing email");

        // then
        assertNull(userByNotExistingEmail);
    }

    @Test
    public void shouldReturnAllEvents() {
        // when
        final Collection<User> allUsers = userDao.getAll();

        // then
        assertEquals(newHashSet(users.values()), newHashSet(allUsers));
    }

    @Test
    public void shouldSaveUser() {
        // given
        final int usersBeforeSave = userDao.getAll().size();

        // when
        final User userForSave = new User(14011L).setFirstName("user").setLastName("forSave");
        userDao.save(userForSave);

        final int usersAfterSave = userDao.getAll().size();

        // then
        assertEquals(usersBeforeSave + 1, usersAfterSave);
    }

}
