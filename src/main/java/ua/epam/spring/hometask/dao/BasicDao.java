package ua.epam.spring.hometask.dao;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Evgeny_Botyanovsky
 */
public interface BasicDao<T> {

    T save(@Nonnull T entity);

    void saveMany(@Nonnull T... entities);

    @Nullable
    T getById(@Nonnull Serializable id);

    @Nonnull
    Collection<T> getAll();

    T update(@Nonnull T entity);

    void delete(@Nonnull T entity);

}
