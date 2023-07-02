package jpabook.jpashop.domain;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;

public interface ResponseOutInterface {

    String getDays();
    String getAptName();
    String getAddress();
    String getStreetAddress();
    String getCompletedYear();
    String getSize();
    String getSize2();
    String getFloor();
    String getType();
    String getPrice();
    String getCancel();
    String getCancelYear();
    String getTradeAddress();

}
