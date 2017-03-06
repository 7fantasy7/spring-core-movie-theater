package ua.epam.spring.hometask.domain.stats;

import ua.epam.spring.hometask.domain.DomainObject;

/**
 * @author Evgeny_Botyanovsky
 */
public class LuckyTicketInfo extends DomainObject {

    private String info;

    public LuckyTicketInfo() {
    }

    public LuckyTicketInfo(Long id) {
        super(id);
    }

    public String getInfo() {
        return info;
    }

    public LuckyTicketInfo setInfo(String info) {
        this.info = info;
        return this;
    }
}
