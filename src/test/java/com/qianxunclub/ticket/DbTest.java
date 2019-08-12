package com.qianxunclub.ticket;

import com.qianxunclub.ticket.repository.dao.TicketDao;
import com.qianxunclub.ticket.repository.entity.Ticket;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbTest {

    @Autowired
    private TicketDao ticketDao;

    private static int id = 123455;

    @Test
    public void addData(){
        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setUsername("1");
        ticket.setPassword("2");
        ticket.setFrom("3");
        ticket.setTo("4");
        ticket.setSeat("5");
        ticket.setMobile("6");
        ticket.setDate("7");
        ticket.setTrainNumber("8");
        ticket.setPassengerCode("9");
        ticket.setRealName("10");
        ticketDao.add(ticket);
    }

    @Test
    public void getData(){
        List<Ticket> ticketList = ticketDao.list();
        System.out.println(ticketList);
    }

    @Test
    public void delData(){
        System.out.println(ticketDao.deleteById(id));
    }

}
