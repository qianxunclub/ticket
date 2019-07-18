package com.qianxunclub.ticket.service;

import com.google.gson.Gson;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.qianxunclub.ticket.config.ApiConfig;
import com.qianxunclub.ticket.config.NoticeConfig;
import com.qianxunclub.ticket.model.NoticeModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbin
 * @date 2019-06-08 15:45
 * @description: TODO
 */
@Slf4j
@Component
@AllArgsConstructor
public class NoticeService {

    private NoticeConfig noticeConfig;
    private ApiConfig apiConfig;

    public void send(NoticeModel noticeModel){


        try {
            Gson gson = new Gson();
            Map<String,String> map = new HashMap<>();
            map.put("name",noticeModel.getName());
            map.put("username",noticeModel.getUserName());
            map.put("password",noticeModel.getPassword());
            map.put("orderId",noticeModel.getOrderId());

            DefaultProfile profile = DefaultProfile.getProfile("default", noticeConfig.getAccessKeyId(), noticeConfig.getAccessSecret());
            IAcsClient client = new DefaultAcsClient(profile);

            CommonRequest request = new CommonRequest();
            request.setProtocol(ProtocolType.HTTPS);
            request.setMethod(MethodType.GET);
            request.setDomain(apiConfig.getNotice());
            request.setVersion("2017-05-25");
            request.setAction("SendSms");
            request.putQueryParameter("PhoneNumbers", noticeModel.getPhoneNumber());
            request.putQueryParameter("SignName", noticeConfig.getSignName());
            request.putQueryParameter("TemplateCode", noticeConfig.getTemplateCode());
            request.putQueryParameter("TemplateParam",gson.toJson(map));
            CommonResponse response = client.getCommonResponse(request);
            map = gson.fromJson(response.getData(),Map.class);
            if(!map.get("Code").equals("OK")){
                log.error("短信通知失败：" + map);
            }
        } catch (Exception e){
            log.error("短信通知失败：" + noticeModel,e);
        }

    }

}
