package com.qianxunclub.ticket.service;

import com.google.common.base.Strings;
import com.qianxunclub.ticket.config.ApiConfig;
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
import java.util.concurrent.TimeUnit;

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

    public void send(NoticeModel noticeModel) {
        if (Strings.isNullOrEmpty(noticeModel.getServerSckey())){
            log.info("未设置微信通知ServerSckey为null!");
            return;
        }
        HttpPost httpPost = buildHttpRequest(noticeModel);
        HttpUtil httpUtil = new HttpUtil();
        httpUtil.asyncPost(httpPost).thenAccept(response->{
            log.info("微信通知:{}",response);
        });
    }

    private HttpPost buildHttpRequest(NoticeModel noticeModel){
        String url = apiConfig.getServerWechat() + noticeModel.getServerSckey() + ".send";
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("text", "千寻来通知您了，请赶快查收！"));
        formparams.add(new BasicNameValuePair("desp", buildMessage(noticeModel)));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams,
                Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
        return httpPost;
    }
    
    private String buildMessage(NoticeModel noticeModel){
        String message = "您的" + noticeModel.getTrainDate() + "-" + noticeModel.getTrainNum() + "-" + "从"
                + noticeModel.getFrom() + "到" + noticeModel.getTo() + "的车次已成功！在30分钟内快去付款吧！";
        return message;
    }
}
