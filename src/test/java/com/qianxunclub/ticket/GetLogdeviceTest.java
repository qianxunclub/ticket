package com.qianxunclub.ticket;

import com.qianxunclub.ticket.config.Config;
import com.qianxunclub.ticket.model.LogdeviceModel;
import com.qianxunclub.ticket.util.LogdeviceUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetLogdeviceTest {

    @Autowired
    private Config config;

    @Test
    public void getLogdevice() {
        LogdeviceModel logdeviceModel = LogdeviceUtil
                .getLogdevice(
                        config.getEnableProxy() ? config.getProxyHost() : null,
                        config.getEnableProxy() ? config.getProxyPort() : 0
                );
        System.out.println(logdeviceModel.toString());
    }

}
