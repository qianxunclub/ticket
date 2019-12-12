package com.qianxunclub.ticket.util;

import com.gargoylesoftware.htmlunit.AjaxController;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qianxunclub.ticket.config.Config;
import com.qianxunclub.ticket.config.CookiesConfig;
import com.qianxunclub.ticket.model.LogdeviceModel;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;
import org.springframework.util.StringUtils;

@Slf4j
public class LogdeviceUtil {

    private static String cookieUrl;

    public static LogdeviceModel getLogdevice() {

        Config config = ApplicationContextHelper.getBean(Config.class);
        CookiesConfig cookiesConfig = ApplicationContextHelper.getBean(CookiesConfig.class);
        if(cookiesConfig.getEnable()){
            return new LogdeviceModel(cookiesConfig.getRailExpiration(),
                    cookiesConfig.getRailDeviceid());
        }
        String proxyHost = config.getEnableProxy() ? config.getProxyIp().getIp() : null;
        int proxyPort = config.getEnableProxy() ? config.getProxyIp().getPort() : 0;
        try {
            String url = LogdeviceUtil.getLogdeviceUrl(proxyHost, proxyPort);
            if (StringUtils.isEmpty(url)) {
                log.warn("获取cookieurl出错，稍后再试");
                return null;
            }
            HttpUtil httpUtil = new HttpUtil();
            HttpGet httpGet = new HttpGet(url);
            String msg = httpUtil.get(httpGet);
            msg = msg.replace("callbackFunction('", "");
            msg = msg.replace("')", "");
            Gson g = new Gson();
            JsonObject obj = g.fromJson(msg, JsonObject.class);
            LogdeviceModel model = new LogdeviceModel(obj.get("exp").getAsString(),
                    obj.get("dfp").getAsString());
            return model;
        } catch (Exception e) {
            log.error("获取cookie出错", e);
        }
        return null;
    }

    public static String getLogdeviceUrl(String proxyHost, int proxyPort) {
        WebClient wc = new WebClient(BrowserVersion.CHROME, proxyHost, proxyPort);
        wc.getOptions().setTimeout(15000);
        wc.getOptions().setUseInsecureSSL(true);
        wc.getOptions().setJavaScriptEnabled(true);
        wc.getOptions().setCssEnabled(false);
        //当JS执行出错的时候是否抛出异常, 这里选择不需要
        wc.getOptions().setThrowExceptionOnScriptError(true);
        //当HTTP的状态非200时是否抛出异常
        wc.getOptions().setThrowExceptionOnFailingStatusCode(true);
        //很重要，设置支持AJAX
        wc.setAjaxController(new AjaxController() {
            @Override
            public boolean processSynchron(HtmlPage page, WebRequest settings, boolean async) {
                return super.processSynchron(page, settings, async);
            }
        });
        wc.setWebConnection(
                new WebConnectionWrapper(wc) {
                    @Override
                    public WebResponse getResponse(WebRequest request) throws IOException {
                        WebResponse response = super.getResponse(request);

                        if (request.getUrl().toExternalForm().contains("/otn/HttpZF/logdevice")) {
                            cookieUrl = request.getUrl().toExternalForm();
                        }
                        return response;

                    }
                }

        );
        try {
            wc.getPage("https://kyfw.12306.cn/otn/leftTicket/init?linktypeid=dc");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            wc.waitForBackgroundJavaScript(3 * 1000);
        }
        while (StringUtils.isEmpty(cookieUrl)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return cookieUrl;
    }

}
