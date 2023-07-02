package jpabook.jpashop.service;

import jpabook.jpashop.domain.Response;
import jpabook.jpashop.repository.ApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApiService {

    private final ApiRepository apiRepository;
    //회원가입
    @Transactional
    public String join(Response.Body.Items.aptItem item) {

        apiRepository.save(item);
        return item.getAptName();
    }

}
