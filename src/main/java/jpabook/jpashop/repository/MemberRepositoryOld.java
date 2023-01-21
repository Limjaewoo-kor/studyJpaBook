package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryOld {

    private final EntityManager em;

    // EntityManager는 @PersistenceContext로 인젝션을 주입해줘야하나,
    // 스프링부트_jpa 에서는 @PersistenceContext를 @Autowired로 인젝션 주입이 가능하도록 해주기에
    // 클래스에 @RequiredArgsConstructor를 선언하면
    // EntityManager에 @PersistenceContext 또는 @Autowired 뺄수 있다.

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class,id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m ", Member.class)
                .getResultList();
        //JPQL에서는 from의 대상이 테이블이 아니라 엔티티이다.
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name= :name",Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}
