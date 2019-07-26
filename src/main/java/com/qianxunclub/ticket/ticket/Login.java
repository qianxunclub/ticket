package com.qianxunclub.ticket.ticket;

import com.qianxunclub.ticket.config.CookiesConfig;
import com.qianxunclub.ticket.model.LogdeviceModel;
import com.qianxunclub.ticket.model.UserModel;
import com.qianxunclub.ticket.model.UserTicketStore;
import com.qianxunclub.ticket.service.ApiRequestService;
import com.qianxunclub.ticket.util.CaptchaImageForPy;
import com.qianxunclub.ticket.util.CookieUtil;

import org.apache.http.impl.client.BasicCookieStore;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbin
 * @date 2019-05-31 14:24
 * @description: TODO
 */
@Slf4j
@AllArgsConstructor
@Component
public class Login {

    private ApiRequestService apiRequestService;
    private CaptchaImageForPy captchaImageForPy;
    private CookieUtil cookieUtil;
    private CookiesConfig cookiesConfig;

    public void init(UserModel userModel) {
        LogdeviceModel logdeviceModel = null;
        // logdeviceModel = apiRequestService.getDeviceId();
        logdeviceModel = new LogdeviceModel(cookiesConfig.getRailExpiration(), cookiesConfig.getRailDeviceid());
        if (userModel.getLogdeviceModel() == null) {
            userModel.setLogdeviceModel(logdeviceModel);
        }
        BasicCookieStore basicCookieStore = UserTicketStore.userBasicCookieStore.get(userModel.getUsername());
        UserTicketStore.userBasicCookieStore.put(userModel.getUsername(), cookieUtil.init(basicCookieStore, userModel.getLogdeviceModel()));


    }

    public boolean login(UserModel userModel) {
        this.init(userModel);
        if (!apiRequestService.isLogin(userModel)) {
            log.info("正在登陆：" + userModel.getUsername());
            if (apiRequestService.isLoginPassCode()) {
                log.info("正在识别验证码");
                String captchaImage = apiRequestService.captchaImage();
                String position = captchaImageForPy.check(captchaImage);
                userModel.setAnswer(position);
            }
            if (apiRequestService.captchaCheck(userModel.getAnswer())) {
                if (apiRequestService.login(userModel)) {
                    log.info("登录成功：" + userModel.getUsername());
                } else {
                    return false;
                }

            } else {
                return false;
            }
        }
        userModel.setUamtk(apiRequestService.uamtk(userModel.getUsername()));
        userModel.setUamtk(apiRequestService.uamauthclient(userModel.getUsername(), userModel.getUamtk()));
        return true;
    }
}
