package com.qianxunclub.ticket;


import com.qianxunclub.ticket.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetQueryTicketSleepTimeTest {

    @Autowired
    private Config config;

    @Test
    public void getQueryTicketSleepTimeTest() {
        for (int i = 0; i < 10; i++) {
            log.info(config.querySleep().toString());
        }
    }

}
