package com.qianxunclub.ticket.ticket;

import com.qianxunclub.ticket.model.TicketInfoModel;
import com.qianxunclub.ticket.model.SeatModel;
import com.qianxunclub.ticket.model.TicketModel;
import com.qianxunclub.ticket.service.ApiRequestService;

import org.apache.commons.lang.StringUtils;
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

    public TicketModel getMyTicket(TicketInfoModel ticketInfoModel) {
        TicketModel ticketModel = this.getTicket(ticketInfoModel);
        List<SeatModel> seat = new ArrayList<>();
        ticketInfoModel.getSeat().forEach(seatLevelEnum -> {
            ticketModel.getSeat().forEach(seatModel -> {
                if (seatModel.getSeatLevel().equals(seatLevelEnum)) {
                    seat.add(seatModel);
                }
            });
        });
        ticketModel.setSeat(seat);
        return ticketModel;
    }


    public TicketModel getTicket(TicketInfoModel ticketInfoModel) {
        AtomicReference<TicketModel> ticketModel = new AtomicReference<>(new TicketModel());
        List<TicketModel> ticketModelList = this.getCanBuyTicket(ticketInfoModel);
        ticketModelList.forEach(ticket -> {
            ticket.setTrainDate(ticketInfoModel.getDate());
            if (ticket.getTrainNumber().equals(ticketInfoModel.getTrainNumber())) {
                List<SeatModel> seat = new ArrayList<>();
                ticket.getSeat().forEach(seatModel -> {
                    if (ticketInfoModel.getSeat().contains(seatModel.getSeatLevel())) {
                        seat.add(seatModel);
                        ticket.setSeat(seat);
                    }
                });
                ticketModel.set(ticket);
            }
        });
        return ticketModel.get();
    }

    private List<TicketModel> getCanBuyTicket(TicketInfoModel ticketInfoModel) {
        List<TicketModel> ticketModelList = apiRequestService.queryTicket(ticketInfoModel);
        List<TicketModel> canBuy = new ArrayList<>();
        ticketModelList.forEach(ticketModel -> {
            if (StringUtils.isNotBlank(ticketModel.getSecret())) {
                List<SeatModel> seatModelList = ticketModel.getSeat();
                List<SeatModel> canBuySeatModelList = new ArrayList<>();
                seatModelList.forEach(seatModel -> {
                    if (StringUtils.isNotBlank(seatModel.getCount()) && !seatModel.getCount().equals("无")) {
                        log.debug("✅车次[" + ticketModel.getTrainNumber() + "]「" + seatModel.getSeatLevel().getName() + "-" + seatModel.getCount() + "」（" + Station.getNameByCode(ticketModel.getFrom()) + ticketModel.getDepartDate() + "-" + Station.getNameByCode(ticketModel.getTo()) + ticketModel.getArriveDate() + "）：可以预定");
                        canBuySeatModelList.add(seatModel);
                    } else {
                        log.debug("❌车次[" + ticketModel.getTrainNumber() + "]「" + seatModel.getSeatLevel().getName() + "-" + seatModel.getCount() + "」（" + Station.getNameByCode(ticketModel.getFrom()) + ticketModel.getDepartDate() + "-" + Station.getNameByCode(ticketModel.getTo()) + ticketModel.getArriveDate() + "）：无票");
                    }
                });
                if (!CollectionUtils.isEmpty(canBuySeatModelList)) {
                    ticketModel.setSeat(canBuySeatModelList);
                    canBuy.add(ticketModel);
                }
            } else {
                log.debug("⚠️车次[" + ticketModel.getTrainNumber() + "]" + "（" + Station.getNameByCode(ticketModel.getFrom()) + "-" + Station.getNameByCode(ticketModel.getTo()) + "）：未开售");
            }
        });

        return canBuy;
    }
}
