package com.qianxunclub.ticket.util;

import com.qianxunclub.ticket.model.LogdeviceModel;
import java.io.File;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.util.StringUtils;

public class LogdeviceUtil {

    public static String webDriverPath = null;

    public static LogdeviceModel getLogdevice() {
        ChromeDriverService chromeDriverService = null;
        try {
            if(webDriverPath == null){
                String os = System.getProperty("os.name");
                webDriverPath = System.getProperty("user.dir") + "/webdriver/";
                if (os.toLowerCase().startsWith("win")) {
                    webDriverPath += "chromedriver_76_wim.exe";
                } else if (os.toLowerCase().startsWith("mac")) {
                    webDriverPath += "chromedriver_76_mac";
                } else {
                    return null;
                }
            }
            System.setProperty("webdriver.chrome.driver", webDriverPath);
            chromeDriverService = new ChromeDriverService.Builder()
                    .usingDriverExecutable(new File(webDriverPath)).usingAnyFreePort().build();
            chromeDriverService.start();
            WebDriver webDriver = new RemoteWebDriver(chromeDriverService.getUrl(),
                    DesiredCapabilities.chrome());
            webDriver.get("https://kyfw.12306.cn/otn/resources/login.html");
            AtomicReference<String> RAIL_DEVICEID = new AtomicReference<>();
            AtomicReference<String> RAIL_EXPIRATION = new AtomicReference<>();
            while (true) {
                Thread.sleep(2000);
                Set<Cookie> cookies = webDriver.manage().getCookies();
                cookies.forEach(cookie -> {
                    if (cookie.getName().equals("RAIL_DEVICEID")) {
                        RAIL_DEVICEID.set(cookie.getValue());
                    }
                    if (cookie.getName().equals("RAIL_EXPIRATION")) {
                        RAIL_EXPIRATION.set(cookie.getValue());
                    }
                });
                if (!StringUtils.isEmpty(RAIL_DEVICEID.get()) && !StringUtils
                        .isEmpty(RAIL_EXPIRATION.get())) {
                    break;
                }
            }
            LogdeviceModel logdeviceModel = new LogdeviceModel(RAIL_EXPIRATION.get(),
                    RAIL_DEVICEID.get());
            return logdeviceModel;
        } catch (Exception e) {

        } finally {
            if (chromeDriverService != null) {
                chromeDriverService.stop();
            }
        }
        return null;
    }

}
