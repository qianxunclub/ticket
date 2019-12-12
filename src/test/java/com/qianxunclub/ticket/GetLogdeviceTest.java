package com.qianxunclub.ticket;

import com.qianxunclub.ticket.model.LogdeviceModel;
import com.qianxunclub.ticket.util.LogdeviceUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetLogdeviceTest {

    @Test
    public void getLogdevice() {
        LogdeviceModel logdeviceModel = LogdeviceUtil.getLogdevice();
        System.out.println("rail_expiration:" + logdeviceModel.getExp());
        System.out.println("rail_deviceid:" + logdeviceModel.getDfp());
    }

}
