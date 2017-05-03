package ua.epam.spring.hometask.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@MappedSuperclass
@XmlRootElement
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

    @XmlElement
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
