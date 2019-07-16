package com.qianxunclub.ticket.handle;

import com.qianxunclub.ticket.model.UserModel;
import com.qianxunclub.ticket.request.Request;
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

    private Request request;
    private CaptchaImageForPy captchaImageForPy;

    public boolean login(UserModel userModel) {
        if (!request.isLogin(userModel)) {
            log.info("正在登陆：" + userModel.getUsername());
            if (request.isLoginPassCode()) {
                String captchaImage = request.captchaImage();
                String position = captchaImageForPy.check(captchaImage);
                userModel.setAnswer(position);
            }
            if (request.captchaCheck(userModel.getAnswer())) {
                if (request.login(userModel)) {
                    log.info("登录成功：" + userModel.getUsername());
                } else {
                    return false;
                }

            } else {
                return false;
            }
        }
        userModel.setUamtk(request.uamtk(userModel.getUsername()));
        userModel.setUamtk(request.uamauthclient(userModel.getUsername(), userModel.getUamtk()));
        return true;
    }
}
