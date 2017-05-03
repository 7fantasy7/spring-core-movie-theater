package ua.epam.spring.hometask.domain.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    public LocalDateTime unmarshal(String date) throws Exception {
        return LocalDateTime.parse(date);
    }

    public String marshal(LocalDateTime date) throws Exception {
        return date.toString();
    }


}