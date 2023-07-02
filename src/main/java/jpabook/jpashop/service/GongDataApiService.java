package jpabook.jpashop.service;

import jpabook.jpashop.domain.Response;
import jpabook.jpashop.domain.ResponseOut;
import jpabook.jpashop.domain.ResponseOutInterface;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GongDataApiService {

    private final ApiRepository repository;

    //회원 전체 조회
    public List<Response.Body.Items.aptItem> findApts() {
        return repository.findAll();
    }

    public List<ResponseOutInterface> findAll2() {
        return repository.findProjectionsByRes();
    }

    public List<ResponseOutInterface> findWhere(String stationName) {
        return repository.findProjectionsByResWhere(stationName);
    }


    public Response.Body.Items.aptItem findByAptName(Long memberId){
        return repository.findById(memberId).get();
    }

}
