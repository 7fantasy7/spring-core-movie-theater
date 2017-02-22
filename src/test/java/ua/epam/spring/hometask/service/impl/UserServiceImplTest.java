package ua.epam.spring.hometask.service.impl;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.dao.impl.UserDaoImpl;
import ua.epam.spring.hometask.domain.User;

/**
 * @author Evgeny_Botyanovsky
 */
public class UserServiceImplTest {

    private UserServiceImpl userService;

    private User user;

    private static long idGenerator = 1;

    @Before
    public void setUp() {
        final UserDao userDao = new UserDaoImpl();
        userService = new UserServiceImpl(userDao);

        user = new User(idGenerator++).setFirstName("first").setLastName("last")
                .setBirthDay(LocalDateTime.now().minusYears(10)).setEmail("user@epam.ua");
    }

    @Test
    public void shouldSaveUser() {
        // given
        final int usersCount = userService.getAll().size();
        final User newUser = new User(idGenerator++).setFirstName("new first").setLastName(" new last")
                .setBirthDay(LocalDateTime.now().minusYears(19));

        // when
        userService.save(newUser);

        // then
        final int usersCountAfterSave = userService.getAll().size();
        assertEquals(usersCount + 1, usersCountAfterSave);
    }

    @Test
    public void shouldGetById() {
        // given
        userService.save(user);

        // when
        final User userFromDB = userService.getById(user.getId());

        // then
        assertNotNull(userFromDB);
    }

    @Test
    public void shouldGetByEmail() {
        // given
        userService.save(user);

        // when
        final User userFromDB = userService.getUserByEmail("user@epam.ua");

        // then
        assertNotNull(userFromDB);
    }

    @Test
    public void shouldRemove() {
        // given
        userService.save(user);

        // when
        userService.remove(user);

        // then
        final User userFromDB = userService.getById(user.getId());
        assertNull(userFromDB);
    }

}
