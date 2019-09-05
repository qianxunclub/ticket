package com.qianxunclub.ticket;


import com.qianxunclub.ticket.constant.Constant;
import com.qianxunclub.ticket.model.UserModel;
import com.qianxunclub.ticket.service.ApiRequestService;
import com.qianxunclub.ticket.ticket.Login;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginTest {

    @Autowired
    private ApiRequestService apiRequestService;

    @Autowired
    private Login login;

    @Test
    public void login() {
        UserModel userModel = new UserModel();
        userModel.setUsername(Constant.USER_NAME);
        userModel.setPassword(Constant.PASSWORD);
        login.login(userModel);
    }
}
