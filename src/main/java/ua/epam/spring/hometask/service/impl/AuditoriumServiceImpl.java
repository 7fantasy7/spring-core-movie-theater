package ua.epam.spring.hometask.service.impl;

import java.util.Collection;
import java.util.Collections;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.service.AuditoriumService;

/**
 * @author Evgeny_Botyanovsky
 */
public class AuditoriumServiceImpl implements AuditoriumService {

    private Collection<Auditorium> auditoriums;

    public AuditoriumServiceImpl(final Collection<Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }

    @Nonnull
    @Override
    public Collection<Auditorium> getAll() {
        return Collections.unmodifiableCollection(auditoriums);
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return auditoriums.stream()
                .filter(auditorium -> name.equals(auditorium.getName()))
                .findAny()
                .orElse(null);
    }

}
