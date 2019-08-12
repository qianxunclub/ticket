package com.qianxunclub.ticket;


import com.qianxunclub.ticket.model.PassengerModel;
import com.qianxunclub.ticket.model.UserModel;
import com.qianxunclub.ticket.service.TicketService;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetPassengerTest {

    @Autowired
    private TicketService ticketService;

    @Test
    public void getPassenger() {

        UserModel userModel = new UserModel();
        // 12306 账号
        userModel.setUsername("XXXX");
        // 12306 密码
        userModel.setPassword("XXXX");
        List<PassengerModel> passengerModelList = ticketService.login(userModel);
        passengerModelList.forEach(passengerModel -> {
            System.out.println("" +
                    "   姓名：" + passengerModel.getPassengerName() +
                    "   证件号：" + passengerModel.getPassengerIdNo() +
                    "   code:" + passengerModel.getCode()
            );
        });
    }
}
