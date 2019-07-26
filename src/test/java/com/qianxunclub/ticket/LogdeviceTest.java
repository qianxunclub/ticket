package com.qianxunclub.ticket;

import com.qianxunclub.ticket.model.LogdeviceModel;
import com.qianxunclub.ticket.model.UserModel;
import com.qianxunclub.ticket.service.ApiRequestService;
import com.qianxunclub.ticket.ticket.Login;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhangbin
 * @date 2019-07-15 17:23
 * @description: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LogdeviceTest {

    @Autowired
    private ApiRequestService apiRequestService;
    @Autowired
    private Login login;

    @Test
    public void getLogdeviceUrl() {

        String logdeviceUrl = apiRequestService.getLogdeviceUrl();
        System.out.println(logdeviceUrl);
    }

    @Test
    public void getDeviceId() {
        LogdeviceModel logdeviceModel = apiRequestService.getDeviceId();
        System.out.println(logdeviceModel);
    }

    @Test
    public void login() {
        UserModel userModel = new UserModel();
        userModel.setUsername("abcd");
        userModel.setPassword("1234");
        userModel.setLogdeviceModel(apiRequestService.getDeviceId());
        login.login(userModel);
    }
}
