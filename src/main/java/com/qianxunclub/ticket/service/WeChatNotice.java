package com.qianxunclub.ticket.service;

import com.google.common.base.Strings;
import com.qianxunclub.ticket.config.ApiConfig;
import com.qianxunclub.ticket.constant.SeatLevelEnum;
import com.qianxunclub.ticket.model.BuyTicketInfoModel;
import com.qianxunclub.ticket.model.NoticeModel;
import com.qianxunclub.ticket.util.HttpUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Juinjonn.Chan
 * @description 微信通知服务  通过调用Server服务 来通知相关信息
 * @date 2019/12/29
 */
@Slf4j
@Service
@AllArgsConstructor
public class WeChatNotice{

    public ApiConfig apiConfig;

    /**
     * 发送微信通知方法
     */
    public void send(String serverSckey,String message,String topic) {
        if (Strings.isNullOrEmpty(serverSckey)){
            log.info("未设置微信通知ServerSckey为null!");
            return;
        }
        HttpPost httpPost = buildHttpRequest(serverSckey,message,topic);
        HttpUtil httpUtil = new HttpUtil();
        httpUtil.asyncPost(httpPost).thenAccept(response->{
            log.info("微信通知:{}",response);
        });
    }

    private HttpPost buildHttpRequest(String serverSckey,String message,String topic){
        String url = apiConfig.getServerWechat() + serverSckey + ".send";
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("text", topic));
        formparams.add(new BasicNameValuePair("desp", message));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams,
                Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
        return httpPost;
    }

    public String buildSuccessMessage(NoticeModel noticeModel){
        String message = "您的" + noticeModel.getTrainDate() + "-" + noticeModel.getTrainNum() + "-" + "从"
                + noticeModel.getFrom() + "到" + noticeModel.getTo() + "的车次已成功！在30分钟内快去付款吧！";
        return message;
    }

    public String buildTicketMessage(BuyTicketInfoModel buyTicketInfoModel) {
        String message = "您想要预定的" + buyTicketInfoModel.getDate() + "-" + buyTicketInfoModel.getTrainNumber() + "-" + "从"
                + buyTicketInfoModel.getFrom() + "到" + buyTicketInfoModel.getTo() + "座位:"
                + SeatLevelEnum.getSeatNameList(buyTicketInfoModel.getSeat()) + "正在放票，赶快去查看！";
        return message;
    }
}
