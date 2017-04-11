package ua.epam.spring.hometask.service.impl;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.service.AuditoriumService;

/**
 * @author Evgeny_Botyanovsky
 */
@Service
@Transactional
public class AuditoriumServiceImpl implements AuditoriumService {

    private Collection<Auditorium> auditoriums;

    private AuditoriumDao auditoriumDao;

    @Autowired
    AuditoriumServiceImpl(final Collection<Auditorium> auditoriums, final AuditoriumDao auditoriumDao) {
        this.auditoriums = auditoriums;
        this.auditoriumDao = auditoriumDao;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void initAuditoriums() throws Exception {
        for (Auditorium auditorium : auditoriums) {
            if (auditoriumDao.getByName(auditorium.getName()) == null) {
                auditoriumDao.save(auditorium);
            }
        }
    }

    @Nonnull
    @Override
    public Collection<Auditorium> getAll() {
        return auditoriumDao.getAll();
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull final String name) {
        return auditoriumDao.getByName(name);
    }

}
