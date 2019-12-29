package com.qianxunclub.ticket.ticket;

import com.qianxunclub.ticket.model.BuyTicketInfoModel;
import com.qianxunclub.ticket.model.SeatModel;
import com.qianxunclub.ticket.model.TicketModel;
import com.qianxunclub.ticket.service.ApiRequestService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbin
 * @date 2019-06-04 14:04
 * @description: TODO
 */
@AllArgsConstructor
@Component
@Slf4j
public class QueryTicket {


    private ApiRequestService apiRequestService;

    public TicketModel getMyTicket(BuyTicketInfoModel buyTicketInfoModel) {
        TicketModel ticketModel = this.getTicket(buyTicketInfoModel);
        List<SeatModel> seat = new ArrayList<>();
        buyTicketInfoModel.getSeat().forEach(seatLevelEnum -> {
            ticketModel.getSeat().forEach(seatModel -> {
                if (seatModel.getSeatLevel().equals(seatLevelEnum)) {
                    seat.add(seatModel);
                }
            });
        });
        ticketModel.setSeat(seat);
        return ticketModel;
    }


    public TicketModel getTicket(BuyTicketInfoModel buyTicketInfoModel) {
        AtomicReference<TicketModel> ticketModel = new AtomicReference<>(new TicketModel());
        List<TicketModel> ticketModelList = this.getCanBuyTicket(buyTicketInfoModel);
        ticketModelList.forEach(ticket -> {
            ticket.setTrainDate(buyTicketInfoModel.getDate());
            if (buyTicketInfoModel.getTrainNumber().contains(ticket.getTrainNumber())) {
                List<SeatModel> seat = new ArrayList<>();
                ticket.getSeat().forEach(seatModel -> {
                    if (buyTicketInfoModel.getSeat().contains(seatModel.getSeatLevel())) {
                        seat.add(seatModel);
                    }
                });
                BeanUtils.copyProperties(ticket,ticketModel.get());
                ticketModel.get().setSeat(seat);
            }
        });
        return ticketModel.get();
    }

    private List<TicketModel> getCanBuyTicket(BuyTicketInfoModel buyTicketInfoModel) {

        apiRequestService.leftTicketInit(buyTicketInfoModel);

        List<TicketModel> ticketModelList = apiRequestService.queryTicket(buyTicketInfoModel);
        List<TicketModel> canBuy = new ArrayList<>();
        ticketModelList.forEach(ticketModel -> {
            if (StringUtils.isNotBlank(ticketModel.getSecret())) {
                List<SeatModel> seatModelList = ticketModel.getSeat();
                List<SeatModel> canBuySeatModelList = new ArrayList<>();
                seatModelList.forEach(seatModel -> {
                    if (StringUtils.isNotBlank(seatModel.getCount()) && !seatModel.getCount().equals("无")) {
                        if(buyTicketInfoModel.getSeat().contains(seatModel.getSeatLevel())&& buyTicketInfoModel.getTrainNumber().equals(ticketModel.getTrainNumber())) {
                            log.info("✅车次[" + ticketModel.getTrainNumber() + "]「" + seatModel.getSeatLevel().getName() + "-" + seatModel.getCount() + "」（" + Station.getNameByCode(ticketModel.getFrom())
                                    + ticketModel.getDepartDate() + "-" + Station.getNameByCode(ticketModel.getTo()) + ticketModel.getArriveDate() + "）：可以预定");
                        }
                        canBuySeatModelList.add(seatModel);
                    } else {
                        if(buyTicketInfoModel.getSeat().contains(seatModel.getSeatLevel()) && buyTicketInfoModel.getTrainNumber().contains(ticketModel.getTrainNumber())) {
                            log.info("❌车次[" + ticketModel.getTrainNumber() + "]「" + seatModel.getSeatLevel().getName() + "-" + seatModel.getCount() + "」（" + Station.getNameByCode(ticketModel.getFrom())
                                    + ticketModel.getDepartDate() + "-" + Station.getNameByCode(ticketModel.getTo()) + ticketModel.getArriveDate() + "）：无票");
                        }
                    }
                });
                if (!CollectionUtils.isEmpty(canBuySeatModelList)) {
                    ticketModel.setSeat(canBuySeatModelList);
                    canBuy.add(ticketModel);
                }
            } else {
                log.info("⚠️车次[" + ticketModel.getTrainNumber() + "]" + "（" + Station.getNameByCode(ticketModel.getFrom()) + "-" + Station.getNameByCode(ticketModel.getTo()) + "）：未开售");
            }
        });

        return canBuy;
    }
}
