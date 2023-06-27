package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="response")
public class Response {

    @XmlElement(name = "header")
    private Header header;

    @XmlElement(name= "body")
    private Body body;

    @Getter
    @Setter    @XmlRootElement(name = "header")
    private static class Header{
        private String resultCode;
        private String resultMsg;
    }
    @Getter
    @Setter    @XmlRootElement(name = "body")
    public static class Body{
        private Items items;
        private String numOfRows;
        private String pageNo;
        private String totalCount;

        @Getter
        @Setter
        @XmlRootElement(name = "items")
        public static class Items{
            private List<Item> item;

            @XmlRootElement(name="item")
            @Entity
            public static class Item{

                @Id
                @GeneratedValue
                @Column(name = "apt_id")
                private Long id;

                @Getter
                @XmlElement(name="년")
                private String year;

                @Getter
                @XmlElement(name="월")
                private String month;

                @Getter
                @XmlElement(name="일")
                private String day;


                @Getter
                @XmlElement(name="아파트")
                private String aptName;

//                @Getter
//                @XmlElement(name="지번")
//                private String address;

                @Getter
                @XmlElement(name="도로명")
                private String address2;

                @Getter
                @XmlElement(name="건축년도")
                private String completedYear;

                @Getter
                @XmlElement(name="전용면적")
                private String size;

                @Getter
                @XmlElement(name="층")
                private String floor;

                @Getter
                @XmlElement(name="거래유형")
                private String type;

                @Getter
                @XmlElement(name="거래금액")
                private String price;

                @Getter
                @XmlElement(name="해제여부")
                private String cancel;

                @Getter
                @XmlElement(name="해제사유발생일")
                private String cancelYear;

                @Getter
                @XmlElement(name="중개사소재지")
                private String tradeAddress;

            }
        }
    }
}