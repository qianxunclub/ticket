package com.qianxunclub.ticket.ticket;

import com.qianxunclub.ticket.config.Config;
import com.qianxunclub.ticket.config.CookiesConfig;
import com.qianxunclub.ticket.model.LogdeviceModel;
import com.qianxunclub.ticket.model.UserModel;
import com.qianxunclub.ticket.model.UserTicketStore;
import com.qianxunclub.ticket.service.ApiRequestService;
import com.qianxunclub.ticket.util.CaptchaImageForPy;
import com.qianxunclub.ticket.util.CookieUtil;

import com.qianxunclub.ticket.util.HttpUtil;
import com.qianxunclub.ticket.util.LogdeviceUtil;
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
    private Config config;

    public void init(UserModel userModel) {
        if (userModel.getLogdeviceModel() == null) {
            LogdeviceModel logdeviceModel = LogdeviceUtil
                    .getLogdevice(
                            config.getEnableProxy() ? config.getProxyIp().getIp() : null,
                            config.getEnableProxy() ? config.getProxyIp().getPort() : 0
                    );
            if (logdeviceModel == null) {
                logdeviceModel = new LogdeviceModel(cookiesConfig.getRailExpiration(),
                        cookiesConfig.getRailDeviceid());
            }
            log.info(logdeviceModel.toString());
            userModel.setLogdeviceModel(logdeviceModel);
        }
        HttpUtil httpUtil = UserTicketStore.httpUtilStore.get(userModel.getUsername());
        if (httpUtil == null) {
            httpUtil = new HttpUtil(cookieUtil.init(null, userModel.getLogdeviceModel()));
            UserTicketStore.httpUtilStore.put(userModel.getUsername(), httpUtil);
        }

    }

    public boolean login(UserModel userModel) {
        this.init(userModel);
        if (!apiRequestService.isLogin(userModel)) {
            log.info("正在登陆：" + userModel.getUsername());
            if (apiRequestService.isLoginPassCode(userModel.getUsername())) {
                log.info("正在识别验证码");
                String captchaImage = apiRequestService.captchaImage(userModel.getUsername());
                String position = captchaImageForPy.check(captchaImage);
                userModel.setAnswer(position);
            }
            if (apiRequestService.captchaCheck(userModel.getUsername(), userModel.getAnswer())) {
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
        userModel.setUamtk(
                apiRequestService.uamauthclient(userModel.getUsername(), userModel.getUamtk()));
        return true;
    }
}
