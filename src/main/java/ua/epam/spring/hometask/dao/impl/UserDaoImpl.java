package ua.epam.spring.hometask.dao.impl;

import javax.annotation.Nonnull;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.User;

/**
 * @author Evgeny_Botyanovsky
 */
@Repository
public class UserDaoImpl extends BasicDaoImpl<User> implements UserDao {

    public UserDaoImpl() {
    }

    @Override
    public User getUserByEmail(@Nonnull String email) {
        return (User) getCurrentSession().createCriteria(getEntityClass())
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

}
