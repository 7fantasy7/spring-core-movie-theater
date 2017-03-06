package ua.epam.spring.hometask.dao;

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
    T getById(@Nonnull Long id);

    @Nonnull
    Collection<T> getAll();

    T update(@Nonnull T entity);

    void delete(@Nonnull T entity);

    void clear();

    void flush();
}
