package com.qianxunclub.ticket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;

/**
 * @author Juinjonn.Chan
 * @description
 * @date 2019/12/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TimeTest {
    @Test
    public void testTime(){
         LocalTime startTime = LocalTime.parse("05:55");
         LocalTime endTime = LocalTime.parse("23:35");
        if (LocalTime.parse("11:30").isAfter(endTime) || LocalTime.parse("11:30").isBefore(startTime)){
            System.out.println("不在抢票范围内，运营时间为:06:00-23:30");
        }
    }
}
