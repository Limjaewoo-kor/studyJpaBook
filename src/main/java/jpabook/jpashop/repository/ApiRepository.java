package jpabook.jpashop.repository;



import jpabook.jpashop.domain.Response;
import jpabook.jpashop.domain.ResponseOut;
import jpabook.jpashop.domain.ResponseOutInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApiRepository extends JpaRepository<Response.Body.Items.aptItem,Long> {

    List<Response.Body.Items.aptItem> findByAptName(String id);

    @Query("select a.year ||'년 ' || a.month||'월 '|| a.day||'일 ' as days, a.aptName as aptName , a.address as address, \n" +
            "a.streetAddress as streetAddress, a.completedYear ||'년 ' as completedYear, round(a.size,0)||' ㎡ ' as size, round(round(a.size,0)/3.3,0)||'평' as size2,\n" +
            " a.floor ||' 층' as floor, a.type as type, a.price||'만원' as price, a.cancel as cancel, a.cancelYear as cancelYear, a.tradeAddress as tradeAddress from AptItem a order by to_date(a.year || LPAD(a.month,2,0) ||LPAD(a.day,2,0),'YYYYMMDD') asc")
    List<ResponseOutInterface> findProjectionsByRes();

    @Query("select a.year ||'년 ' || a.month||'월 '|| a.day||'일 ' as days, a.aptName as aptName , a.address as address, \n" +
            "a.streetAddress as streetAddress, a.completedYear ||'년 ' as completedYear, round(a.size,0)||' ㎡ ' as size, round(round(a.size,0)/3.3,0)||'평' as size2,\n" +
            " a.floor ||' 층' as floor, a.type as type, a.price||'만원' as price, a.cancel as cancel, a.cancelYear as cancelYear, a.tradeAddress as tradeAddress from AptItem a where a.streetAddress like %:stationName% order by to_date(a.year || LPAD(a.month,2,0) ||LPAD(a.day,2,0),'YYYYMMDD') asc")
    List<ResponseOutInterface> findProjectionsByResWhere(@Param("stationName") String stationName);
}
