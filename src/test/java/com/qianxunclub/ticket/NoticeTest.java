package com.qianxunclub.ticket;

import com.qianxunclub.ticket.model.NoticeModel;
import com.qianxunclub.ticket.service.NoticeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class NoticeTest {

    @Autowired
    private NoticeService noticeService;

    @Test
    public void send(){

        NoticeModel noticeModel = new NoticeModel();
        noticeModel.setName("张斌");
        noticeModel.setUserName("adsfgh");
        noticeModel.setPassword("54324567");
        noticeModel.setPhoneNumber("3456765432");
        noticeModel.setOrderId("wsadhjkhgf");
        noticeService.send(noticeModel);
    }

}
