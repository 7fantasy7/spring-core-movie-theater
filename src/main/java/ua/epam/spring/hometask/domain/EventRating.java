package ua.epam.spring.hometask.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum
public enum EventRating {

    LOW,

    MID,

    HIGH

}
