package ua.epam.spring.hometask.dao.impl;


import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.epam.spring.hometask.dao.BasicDao;
import ua.epam.spring.hometask.dao.util.HibernateUtil;

/**
 * @author Evgeny_Botyanovsky
 */
@Repository
public abstract class BasicDaoImpl<T> implements BasicDao<T> {

    @Override
    public final T save(@Nonnull T entity) {
        final Session session = getCurrentSession();
        session.save(entity);
        return entity;
    }

    @SafeVarargs
    @Override
    public final void saveMany(@Nonnull T... entities) {
        for (T entity : entities) {
            save(entity);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public final T getById(@Nonnull Long id) {
        return getCurrentSession().get(getEntityClass(), id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public final Collection<T> getAll() {
        final Criteria criteria = getCurrentSession()
                .createCriteria(getEntityClass())
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<T>) criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public final T update(@Nonnull T entity) {
        return (T) getCurrentSession().merge(entity);
    }

    @Override
    public final void delete(@Nonnull T entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public final void clear() {
        getCurrentSession().clear();
    }

    @Override
    public final void flush() {
        getCurrentSession().flush();
    }

    protected abstract Class<T> getEntityClass();

    protected Session getCurrentSession() {
        return HibernateUtil.getCurrentSession();
    }

}
