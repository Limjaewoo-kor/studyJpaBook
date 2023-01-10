package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class ItemServiceTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemService itemService;

    @Test
    public void 아이템저장() throws Exception {
        //given
        Item item = new Item();
        item.setName("itmeA");

        //when
        Long aLong = itemService.saveItem(item);


        //then
        assertEquals(item,itemService.findOne(aLong));
    
    }
    
    @Test
    public void 아이템_전체_조회() throws Exception {
        //given
        Item item = new Item();
        Item item2 = new Item();

        item.setName("itemA");
        item2.setName("itemB");
        //when
        itemService.saveItem(item);
        itemService.saveItem(item2);

        //then
        assertEquals(2,itemService.findItems().size());
        assertEquals("itemA",itemService.findItems().get(0).getName());
        assertEquals("itemB",itemService.findItems().get(1).getName());

    }
}