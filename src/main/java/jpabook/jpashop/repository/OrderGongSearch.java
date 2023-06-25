package jpabook.jpashop.repository;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderGongSearch {

    private String stationName;
    private OrderStatus orderStatus;
}
