package ua.epam.spring.hometask.dao;

import javax.annotation.Nonnull;

import ua.epam.spring.hometask.domain.Auditorium;

/**
 * @author Evgeny_Botyanovsky
 */
public interface AuditoriumDao extends BasicDao<Auditorium> {

    Auditorium getByName(@Nonnull String name);

}
