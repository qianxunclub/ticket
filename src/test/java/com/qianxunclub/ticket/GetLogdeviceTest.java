package com.qianxunclub.ticket;

import com.qianxunclub.ticket.model.LogdeviceModel;
import com.qianxunclub.ticket.util.LogdeviceUtil;
import java.io.File;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.util.StringUtils;

public class GetLogdeviceTest {

    public static void main(String[] args) throws Exception {
        LogdeviceModel logdeviceModel = LogdeviceUtil.getLogdevice();
        System.out.println(logdeviceModel.toString());
    }

}
