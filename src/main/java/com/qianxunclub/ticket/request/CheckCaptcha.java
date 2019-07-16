package com.qianxunclub.ticket.request;

import com.qianxunclub.ticket.util.CaptchaImageForPy;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbin
 * @date 2019-06-11 14:33
 * @description: TODO
 */
@Slf4j
@AllArgsConstructor
@Component
public class CheckCaptcha {
    private CaptchaImageForPy captchaImageForPy;

    public String check(String base64) {
        return captchaImageForPy.check(base64);
    }
}
