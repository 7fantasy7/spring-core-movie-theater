package ua.epam.spring.hometask.dao.impl;

import javax.annotation.Nonnull;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;

/**
 * @author Evgeny_Botyanovsky
 */
@Repository
public class AuditoriumDaoImpl extends BasicDaoImpl<Auditorium> implements AuditoriumDao{

    @Override
    public Auditorium getByName(@Nonnull String name) {
        System.out.println(name);
        return (Auditorium) getCurrentSession().createCriteria(getEntityClass())
                .add(Restrictions.eq("name", name))
                .uniqueResult();
    }
    @Override
    protected Class<Auditorium> getEntityClass() {
        return Auditorium.class;
    }
}