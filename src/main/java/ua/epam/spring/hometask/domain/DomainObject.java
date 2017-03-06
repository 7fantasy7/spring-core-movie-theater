package ua.epam.spring.hometask.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author Yuriy_Tkach
 */
@MappedSuperclass
public abstract class DomainObject {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    public DomainObject() {
    }

    public DomainObject(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
