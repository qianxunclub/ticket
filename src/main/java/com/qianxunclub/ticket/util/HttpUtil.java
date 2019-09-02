package com.qianxunclub.ticket.util;


import com.qianxunclub.ticket.config.Config;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbin
 * @date 2019-05-30 16:34
 * @description: TODO
 */
@Slf4j
@Component
public class HttpUtil {

    private HttpClient httpClient;
    private BasicCookieStore basicCookieStore;

    public HttpUtil() {
        init();
    }

    public void init() {
        BasicCookieStore basicCookieStore = new BasicCookieStore();
        this.init(basicCookieStore);
    }

    public void init(BasicCookieStore basicCookieStore) {
        this.basicCookieStore = basicCookieStore;
        Config config = ApplicationContextHelper.getBean(Config.class);
        httpClient = HttpClients.custom().setDefaultCookieStore(basicCookieStore).build();
        if(config != null && config.getEnableProxy()){
            HttpHost proxy = new HttpHost(config.getProxyHost(),config.getProxyPort());
            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
            httpClient = HttpClients.custom().setRoutePlanner(routePlanner).setDefaultCookieStore(basicCookieStore).build();
        }
    }

    public BasicCookieStore getBasicCookieStore() {
        return basicCookieStore;
    }

    public String get(HttpGet httpGet) {
        return this.doAction(httpGet);
    }


    public String post(HttpPost httpPost) {
        return this.doAction(httpPost);
    }

    private String doAction(HttpRequestBase httpRequestBase) {
        String result = null;
        try {
            HttpResponse response = httpClient.execute(httpRequestBase);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = response.getEntity();
                result = EntityUtils.toString(httpEntity, "UTF-8");
                EntityUtils.consume(httpEntity);
            } else {
                log.error("请求异常：{},{}", httpRequestBase.getURI(), response.getStatusLine());
            }
            return result;
        } catch (IOException e) {
            log.error("请求异常：{},{}", httpRequestBase.getURI(), e.getMessage());
        }
        return "";
    }
}
