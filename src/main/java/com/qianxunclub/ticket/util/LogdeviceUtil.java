package com.qianxunclub.ticket.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qianxunclub.ticket.model.LogdeviceModel;
import java.io.File;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import com.qianxunclub.ticket.service.GetJsCookieService;
import com.qianxunclub.ticket.service.task.GetCookieTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.util.StringUtils;

@Slf4j
public class LogdeviceUtil {

    public static String webDriverPath = null;

    public static LogdeviceModel getLogdevice() {
        try
        {
            String url = GetCookieTask.getCookieUrl();
            if (StringUtils.isEmpty(url))
            {
                log.warn("获取cookieurl出错，稍后再试");
                return null;
            }
            HttpUtil httpUtil = new HttpUtil();
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Host","kyfw.12306.cn");
            String msg = httpUtil.get(httpGet);

            msg = msg.replace("callbackFunction('","");
            msg = msg.replace("')","");
            Gson g = new Gson();
            JsonObject obj = g.fromJson(msg, JsonObject.class);

            LogdeviceModel model = new LogdeviceModel(obj.get("exp").getAsString(),obj.get("dfp").getAsString());
            return model;
        }
        catch (Exception e)
        {
            log.error("获取cookie出错",e);
        }
        return null;
    }

}
