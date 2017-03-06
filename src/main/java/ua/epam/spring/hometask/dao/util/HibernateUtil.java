package ua.epam.spring.hometask.dao.util;


import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.SessionHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author Evgeny_Botyanovsky
 */
@Component
public class HibernateUtil {

    private static SessionFactory SESSION_FACTORY;

    @Autowired
    void setSessionFactory(SessionFactory sessionFactory) {
        SESSION_FACTORY = sessionFactory;
        //SESSION_FACTORY.openSession();
    }

    public static Session getCurrentSession() {
        Optional<Session> springSession = getActiveSpringSession();
        return springSession.orElse(SESSION_FACTORY.getCurrentSession());
    }

    private static Optional<Session> getActiveSpringSession() {
        SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.getResource(SESSION_FACTORY);
        return Optional.ofNullable(sessionHolder).map(SessionHolder::getSession);
    }

    private HibernateUtil() {
    }
}
