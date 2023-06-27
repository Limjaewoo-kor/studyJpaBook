package jpabook.jpashop.repository;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ApiRepository extends JpaRepository<Response.Body.Items.Item,Long> {

    List<Response.Body.Items.Item> findByAptName(String id);
}
