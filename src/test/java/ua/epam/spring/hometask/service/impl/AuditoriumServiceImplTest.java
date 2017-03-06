package ua.epam.spring.hometask.service.impl;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ua.epam.spring.hometask.dao.impl.AuditoriumDaoImpl;
import ua.epam.spring.hometask.domain.Auditorium;

/**
 * @author Evgeny_Botyanovsky
 */
public class AuditoriumServiceImplTest {

    private AuditoriumServiceImpl auditoriumService;

    private Auditorium two = new Auditorium().setName("two");
    private Set<Auditorium> auditoriums = newHashSet(new Auditorium().setName("One"),
            two, new Auditorium().setName("three"));

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        auditoriumService = new AuditoriumServiceImpl(auditoriums, new AuditoriumDaoImpl());
    }

    @Test
    public void shouldReturnAllAuditoriums() {
        // when
        final Collection<Auditorium> allAuditoriums = auditoriumService.getAll();

        // then
        assertEquals(auditoriums.size(), allAuditoriums.size());
    }

    @Test
    public void shouldReturnAuditoriumByName() {
        // when
        final Auditorium auditorium = auditoriumService.getByName("two");

        //then
        assertEquals(two, auditorium);
    }

    @Test
    public void shouldReturnNullIfAuditoriumByNameNotFound() {
        // when
        final Auditorium auditorium = auditoriumService.getByName("not existing name");

        //then
        assertNull(auditorium);
    }

}