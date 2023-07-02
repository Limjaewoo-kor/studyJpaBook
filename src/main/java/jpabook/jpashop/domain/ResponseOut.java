package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
public class ResponseOut {

        @Getter
        @XmlElement(name = "년")
        private String days;

        @Getter
        @XmlElement(name = "아파트")
        private String aptName;

        @Getter
        @XmlElement(name = "법정동")
        private String address;

        @Getter
        @XmlElement(name = "도로명")
        private String streetAddress;

        @Getter
        @XmlElement(name = "건축년도")
        private String completedYear;

        @Getter
        @XmlElement(name = "전용면적")
        private String size;

        @Getter
        @XmlElement(name = "평")
        private String size2;

        @Getter
        @XmlElement(name = "층")
        private String floor;

        @Getter
        @XmlElement(name = "거래유형")
        private String type;

        @Getter
        @XmlElement(name = "거래금액")
        private String price;

        @Getter
        @XmlElement(name = "해제여부")
        private String cancel;

        @Getter
        @XmlElement(name = "해제사유발생일")
        private String cancelYear;

        @Getter
        @XmlElement(name = "중개사소재지")
        private String tradeAddress;

}