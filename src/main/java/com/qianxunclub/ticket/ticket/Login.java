package com.qianxunclub.ticket.ticket;

import com.qianxunclub.ticket.model.UserModel;
import com.qianxunclub.ticket.service.ApiRequestService;
import com.qianxunclub.ticket.util.CaptchaImageForPy;

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

    public boolean login(UserModel userModel) {
        if (!apiRequestService.isLogin(userModel)) {
            log.info("正在登陆：" + userModel.getUsername());
            if (apiRequestService.isLoginPassCode()) {
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
