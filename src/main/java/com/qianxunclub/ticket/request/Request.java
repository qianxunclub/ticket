package com.qianxunclub.ticket.request;

import com.google.gson.Gson;

import com.qianxunclub.ticket.config.Api;
import com.qianxunclub.ticket.config.Config;
import com.qianxunclub.ticket.config.Station;
import com.qianxunclub.ticket.model.LogdeviceModel;
import com.qianxunclub.ticket.model.PassengerModel;
import com.qianxunclub.ticket.model.MyTicketInfoModel;
import com.qianxunclub.ticket.model.TicketModel;
import com.qianxunclub.ticket.model.UserModel;
import com.qianxunclub.ticket.thread.UserInfo;
import com.qianxunclub.ticket.util.CommonUtils;
import com.qianxunclub.ticket.util.HttpUtil;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbin
 * @date 2019-05-30 16:17
 * @description: TODO
 */
@Slf4j
@Component
@AllArgsConstructor
public class Request {

    private Api api;
    private HttpUtil httpUtil;
    private Config config;

    public Map<String, String> station() {
        Map<String, String> stationMap = new HashMap<>();
        HttpGet httpGet = new HttpGet(api.getStation());
        String response = httpUtil.get(httpGet);
        String[] all = response.split("@");
        for (int i = 0; i < all.length; i++) {
            String[] station = all[i].split("[|]");
            if (station.length == 6) {
                stationMap.put(station[1], station[2]);
            }
        }
        return stationMap;
    }

    public String algID() {
        HttpGet httpGet = new HttpGet(api.getGetJs());
        String response = httpUtil.get(httpGet);
        String regex = "algID\\\\x3d(.*?)\\\\x26";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(response);
        while (m.find()) {
            return m.group(1);
        }
        return null;
    }

    public String getLogdeviceUrl() {
        try {
            StringBuffer sb = new StringBuffer();
            FileReader reader = new FileReader(ResourceUtils.getFile("classpath:common.js"));
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
            engine.eval(new StringReader(sb.toString()));
            Invocable invocable = (Invocable) engine;
            String logdevice = (String) invocable.invokeFunction("Test");
            return (config.getBaseUrl() + String.format(logdevice, this.algID())).replaceAll(" ", "%20");
        } catch (Exception e) {
        }
        return null;
    }

    public LogdeviceModel getDeviceId() {
        HttpGet httpGet = new HttpGet(getLogdeviceUrl());
        String response = httpUtil.get(httpGet);
        Gson jsonResult = new Gson();
        String m = response.replaceFirst("callbackFunction", "").replaceAll("\\(", "")
                .replaceAll("\\)", "").replaceAll("'", "");
        Map<String, String> rsmap = jsonResult.fromJson(m, Map.class);
        return new LogdeviceModel(rsmap.get("exp"), rsmap.get("dfp"));
    }

    public List<TicketModel> queryTicket(MyTicketInfoModel myTicketInfoModel) {
        String url = String.format(api.getLeftTicket(), myTicketInfoModel.getDate(), Station.getCodeByName(myTicketInfoModel.getFrom()), Station.getCodeByName(myTicketInfoModel.getTo()));
        HttpGet httpGet = new HttpGet(url);
        String response = httpUtil.get(httpGet);
        if (StringUtils.isEmpty(response)) {
            log.error("车票查询失败!");
            return null;
        }
        Gson jsonResult = new Gson();
        Map<String, Object> rsmap = jsonResult.fromJson(response, Map.class);
        Map data = (Map) rsmap.get("data");
        List<String> table = (List<String>) data.get("result");
        List<TicketModel> ticketModelList = new ArrayList<>();
        table.forEach(v -> {
            TicketModel ticketModel = new TicketModel();
            String[] info = v.split("[|]");
            ticketModel.setInfo(info);
            ticketModelList.add(ticketModel);
        });
        return ticketModelList;
    }

    public boolean isLoginPassCode() {
        HttpGet httpGet = new HttpGet(api.getLoginConfig());
        String response = httpUtil.get(httpGet);
        Gson jsonResult = new Gson();
        Map rsmap = jsonResult.fromJson(response, Map.class);
        Map data = (Map) rsmap.get("data");
        String isLoginPassCode = (String) data.get("is_login_passCode");
        return isLoginPassCode.equals("Y");
    }

    public String captchaImage() {
        HttpGet httpGet = new HttpGet(String.format(api.getCaptchaImage(), Math.random()));
        String response = httpUtil.get(httpGet);
        Gson jsonResult = new Gson();
        Map rsmap = jsonResult.fromJson(response, Map.class);
        return (String) rsmap.get("image");
    }

    public boolean captchaCheck(String answer) {
        HttpGet httpGet = new HttpGet(String.format(api.getCaptchaCheck(), answer, Math.random()));
        String response = httpUtil.get(httpGet);
        Gson jsonResult = new Gson();
        Map rsmap = jsonResult.fromJson(response, Map.class);
        if (!rsmap.getOrDefault("result_code", "").toString().equals("4")) {
            log.error("验证码错误", rsmap.get("result_message"));
            return false;
        }
        log.info("验证码校验成功");
        return true;
    }

    public boolean isLogin(UserModel userModel) {
        httpUtil.init(UserInfo.userBasicCookieStore.get(userModel.getUsername()));
        HttpGet httpGet = new HttpGet(api.getUamtkStatic());
        String response = httpUtil.get(httpGet);
        Gson jsonResult = new Gson();
        Map rsmap = jsonResult.fromJson(response, Map.class);
        if ("0.0".equals(rsmap.get("result_code").toString())) {
            userModel.setUamtk(rsmap.get("newapptk").toString());
            UserInfo.userBasicCookieStore.put(userModel.getUsername(), httpUtil.getBasicCookieStore());
            return true;
        } else {
            return false;
        }
    }

    public boolean login(UserModel userModel) {
        httpUtil.init(UserInfo.userBasicCookieStore.get(userModel.getUsername()));
        HttpPost httpPost = new HttpPost(api.getLogin());
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("username", userModel.getUsername()));
        formparams.add(new BasicNameValuePair("password", userModel.getPassword()));
        formparams.add(new BasicNameValuePair("appid", "otn"));
        formparams.add(new BasicNameValuePair("answer", userModel.getAnswer()));

        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
        String response = httpUtil.post(httpPost);
        if (StringUtils.isEmpty(response)) {
            log.error("登录失败!");
            return false;
        }
        Gson jsonResult = new Gson();
        Map rsmap = jsonResult.fromJson(response, Map.class);
        if (!"0.0".equals(rsmap.getOrDefault("result_code", "").toString())) {
            log.error("登陆失败：", rsmap);
            return false;
        }
        UserInfo.userBasicCookieStore.put(userModel.getUsername(), httpUtil.getBasicCookieStore());
        userModel.setUamtk(rsmap.get("uamtk").toString());
        return true;
    }

    public String uamtk(String userName) {
        httpUtil.init(UserInfo.userBasicCookieStore.get(userName));
        HttpPost httpPost = new HttpPost(api.getUamtk());
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("appid", "otn"));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
        Gson jsonResult = new Gson();
        String response = httpUtil.post(httpPost);
        Map rsmap = jsonResult.fromJson(response, Map.class);
        if ("0.0".equals(rsmap.getOrDefault("result_code", "").toString())) {
            UserInfo.userBasicCookieStore.put(userName, httpUtil.getBasicCookieStore());
            return rsmap.get("newapptk").toString();
        }
        return null;
    }

    public String uamauthclient(String userName, String tk) {
        httpUtil.init(UserInfo.userBasicCookieStore.get(userName));
        HttpPost httpPost = new HttpPost(api.getUamauthclient());
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("tk", tk));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
        Gson jsonResult = new Gson();
        String response = httpUtil.post(httpPost);
        Map rsmap = jsonResult.fromJson(response, Map.class);
        if ("0.0".equals(rsmap.getOrDefault("result_code", "").toString())) {
            UserInfo.userBasicCookieStore.put(userName, httpUtil.getBasicCookieStore());
            return rsmap.get("apptk").toString();
        }
        return null;
    }


    public boolean checkUser(String userName) {
        httpUtil.init(UserInfo.userBasicCookieStore.get(userName));
        HttpPost httpPost = new HttpPost(api.getCheckUser());
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("_json_att", ""));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
        Gson jsonResult = new Gson();
        String response = httpUtil.post(httpPost);
        if (StringUtils.isEmpty(response)) {
            log.error("用户校验失败，不能下单!");
            return false;
        }
        Map rsmap = jsonResult.fromJson(response, Map.class);
        rsmap = jsonResult.fromJson(rsmap.getOrDefault("data", "").toString(), Map.class);
        if (rsmap.getOrDefault("flag", "").toString().equals("true")) {
            log.info("用户校验成功，准备下单购票");
            return true;
        }
        log.error("用户校验失败，不能下单!");
        return false;
    }


    public boolean submitOrderRequest(MyTicketInfoModel myTicketInfoModel, TicketModel ticketModel) {
        httpUtil.init(UserInfo.userBasicCookieStore.get(myTicketInfoModel.getUsername()));
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        HttpPost httpPost = new HttpPost(api.getSubmitOrderRequest());
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("back_train_date", shortSdf.format(cal.getTime())));
        formparams.add(new BasicNameValuePair("purpose_codes", "ADULT"));
        formparams.add(new BasicNameValuePair("query_from_station_name", Station.getNameByCode(myTicketInfoModel.getTo())));
        formparams.add(new BasicNameValuePair("query_to_station_name", Station.getNameByCode(myTicketInfoModel.getFrom())));
        formparams.add(new BasicNameValuePair("secretStr", ticketModel.getSecret()));
        formparams.add(new BasicNameValuePair("train_date", myTicketInfoModel.getDate()));
        formparams.add(new BasicNameValuePair("tour_flag", "dc"));
        formparams.add(new BasicNameValuePair("undefined", ""));

        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
        String response = httpUtil.post(httpPost);
        Gson jsonResult = new Gson();
        Map rsmap = jsonResult.fromJson(response, Map.class);
        if (null != rsmap.get("status") && rsmap.get("status").toString().equals("true")) {
            log.info("点击预定按钮成功");
            return true;

        } else if (null != rsmap.get("status") && rsmap.get("status").toString().equals("false")) {
            String errMsg = rsmap.get("messages") + "";
            log.error(errMsg);
            if (errMsg.contains("未处理的订单")) {
                log.error("您有未完成订单，请处理");
            } else if (errMsg.contains("当前时间不可以订票")) {
                log.error("系统维护时间不能订票");
            }
        }
        log.error("预定时候出错了：" + response);
        return false;
    }

    public String initDc(String userName) {
        httpUtil.init(UserInfo.userBasicCookieStore.get(userName));
        String token = "";
        HttpGet httpGet = new HttpGet(api.getInitDc());
        String response = httpUtil.get(httpGet);
        String regex = "globalRepeatSubmitToken \\= '(.*?)';";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(response);
        while (m.find()) {
            token = m.group(1);
        }
        regex = "'key_check_isChange':'(.*?)',";
        Pattern p1 = Pattern.compile(regex);
        Matcher m1 = p1.matcher(response);
        while (m1.find()) {
            token += "," + m1.group(1);
        }
        return token;
    }

    public List<PassengerModel> getPassengerDTOs(String token) {
        List<PassengerModel> passengerModelList = new ArrayList<>();
        HttpPost httpPost = new HttpPost(api.getGetPassengerDTOs());
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("_json_att", ""));
        formparams.add(new BasicNameValuePair("REPEAT_SUBMIT_TOKEN", token));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
        Gson jsonResult = new Gson();
        String response = httpUtil.post(httpPost);
        Map rsmap = jsonResult.fromJson(response, Map.class);
        if (rsmap.getOrDefault("status", "").toString().equals("true")) {
            rsmap = (Map<String, Object>) rsmap.get("data");
            List<Map<String, String>> passengers = (List<Map<String, String>>) rsmap.get("normal_passengers");
            if (!CollectionUtils.isEmpty(passengers)) {
                passengers.forEach(passenger -> {
                    PassengerModel passengerModel = new PassengerModel(passenger);
                    passengerModelList.add(passengerModel);
                });
            }
        }
        return passengerModelList;
    }

    public String checkOrderInfo(MyTicketInfoModel myTicketInfoModel) {
        httpUtil.init(UserInfo.userBasicCookieStore.get(myTicketInfoModel.getUsername()));
        HttpPost httpPost = new HttpPost(api.getCheckOrderInfo());
        List<NameValuePair> formparams = new ArrayList<>();

        formparams.add(new BasicNameValuePair("bed_level_order_num", "000000000000000000000000000000"));
        formparams.add(new BasicNameValuePair("cancel_flag", "2"));
        formparams.add(new BasicNameValuePair("whatsSelect", "2"));
        formparams.add(new BasicNameValuePair("_json_att", ""));
        formparams.add(new BasicNameValuePair("tour_flag", "dc"));
        formparams.add(new BasicNameValuePair("randCode", ""));
        formparams.add(new BasicNameValuePair("passengerTicketStr", myTicketInfoModel.getPassengerModel().getPassengerTicketStr(myTicketInfoModel)));
        formparams.add(new BasicNameValuePair("REPEAT_SUBMIT_TOKEN", myTicketInfoModel.getGlobalRepeatSubmitToken()));
        formparams.add(new BasicNameValuePair("getOldPassengerStr", myTicketInfoModel.getPassengerModel().getOldPassengerStr(myTicketInfoModel.getPassengerModel())));

        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
        String response = httpUtil.post(httpPost);
        Gson jsonResult = new Gson();
        Map rsmap = jsonResult.fromJson(response, Map.class);
        if (rsmap.getOrDefault("status", "").toString().equals("true")) {
            rsmap = (Map<String, Object>) rsmap.get("data");
            String isShowPassCode = rsmap.get("ifShowPassCode").toString();
            log.info("开始提交订单");
            return isShowPassCode;
        }
        log.error("开始提交订单失败");
        return null;
    }

    public boolean checkRandCodeAnsyn(String position, String token) {
        HttpPost httpPost = new HttpPost(api.getCheckRandCodeAnsyn());
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("randCode", position));
        formparams.add(new BasicNameValuePair("REPEAT_SUBMIT_TOKEN", token));
        formparams.add(new BasicNameValuePair("rand", "randp"));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
        String response = httpUtil.post(httpPost);

        Gson jsonResult = new Gson();
        Map rsmap = jsonResult.fromJson(response, Map.class);
        if (rsmap.getOrDefault("status", "").toString().equals("true")) {
            return true;
        }
        log.error("验证码错误", rsmap.get("result_message"));
        return false;
    }

    public boolean getQueueCount(MyTicketInfoModel myTicketInfoModel, TicketModel ticketModel) {
        httpUtil.init(UserInfo.userBasicCookieStore.get(myTicketInfoModel.getUsername()));
        HttpPost httpPost = new HttpPost(api.getGetQueueCount());
        List<NameValuePair> formparams = new ArrayList<>();

        formparams.add(new BasicNameValuePair("fromStationTelecode", ticketModel.getFrom()));
        formparams.add(new BasicNameValuePair("toStationTelecode", ticketModel.getTo()));
        formparams.add(new BasicNameValuePair("leftTicket", ticketModel.getLeftTicket()));
        formparams.add(new BasicNameValuePair("purpose_codes", "00"));
        formparams.add(new BasicNameValuePair("REPEAT_SUBMIT_TOKEN", myTicketInfoModel.getGlobalRepeatSubmitToken()));
        formparams.add(new BasicNameValuePair("seatType", ticketModel.getSeat().get(0).getSeatLevel().getCode()));
        formparams.add(new BasicNameValuePair("stationTrainCode", ticketModel.getTrainNumber()));
        formparams.add(new BasicNameValuePair("train_date", CommonUtils.getGMT(ticketModel.getTrainDate())));
        formparams.add(new BasicNameValuePair("train_location", ticketModel.getTrainLocation()));
        formparams.add(new BasicNameValuePair("train_no", ticketModel.getTrainCode()));
        formparams.add(new BasicNameValuePair("_json_att", ""));

        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
        String response = httpUtil.post(httpPost);
        Gson jsonResult = new Gson();
        Map rsmap = jsonResult.fromJson(response, Map.class);
        if (rsmap.getOrDefault("status", "").toString().equals("true")) {
            rsmap = (Map) rsmap.get("data");
            String ticket = rsmap.get("ticket").toString();
            String countT = rsmap.get("countT").toString();

            log.info("下单检查成功，余票:" + ticket + "，排队人数：" + countT);
            return true;
        }
        log.error("确认订单失败！");
        return false;

    }

    public boolean confirmSingleForQueue(MyTicketInfoModel myTicketInfoModel, TicketModel ticketModel) {
        httpUtil.init(UserInfo.userBasicCookieStore.get(myTicketInfoModel.getUsername()));
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HttpPost httpPost = new HttpPost(api.getConfirmSingleForQueue());

        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("dwAll", "N"));
        formparams.add(new BasicNameValuePair("purpose_codes", "00"));
        formparams.add(new BasicNameValuePair("key_check_isChange", myTicketInfoModel.getKeyCheckIsChange()));
        formparams.add(new BasicNameValuePair("_json_att", ""));
        formparams.add(new BasicNameValuePair("leftTicketStr", ticketModel.getLeftTicket()));
        formparams.add(new BasicNameValuePair("train_location", ticketModel.getTrainLocation()));
        formparams.add(new BasicNameValuePair("choose_seats", ""));
        formparams.add(new BasicNameValuePair("whatsSelect", "1"));
        formparams.add(new BasicNameValuePair("roomType", "00"));
        formparams.add(new BasicNameValuePair("seatDetailType", "000"));
        formparams.add(new BasicNameValuePair("randCode", ""));
        formparams.add(new BasicNameValuePair("passengerTicketStr", myTicketInfoModel.getPassengerModel().getPassengerTicketStr(myTicketInfoModel)));
        formparams.add(new BasicNameValuePair("REPEAT_SUBMIT_TOKEN", myTicketInfoModel.getGlobalRepeatSubmitToken()));
        formparams.add(new BasicNameValuePair("getOldPassengerStr", myTicketInfoModel.getPassengerModel().getOldPassengerStr(myTicketInfoModel.getPassengerModel())));

        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
        String response = httpUtil.post(httpPost);
        Gson jsonResult = new Gson();
        Map rsmap = jsonResult.fromJson(response, Map.class);
        if (rsmap.getOrDefault("status", "").toString().equals("true")) {
            rsmap = jsonResult.fromJson(rsmap.get("data").toString(), Map.class);
            String subStatus = rsmap.get("submitStatus").toString();
            if (subStatus.equals("true")) {
                log.info("确认提交订单成功");
                return true;
            } else {
                String errMsg = rsmap.get("errMsg").toString();
                log.error("确认提交订单失败:" + errMsg);
            }
        }
        return false;
    }

    public String queryOrderWaitTime(MyTicketInfoModel myTicketInfoModel) {
        httpUtil.init(UserInfo.userBasicCookieStore.get(myTicketInfoModel.getUsername()));
        int m = 5;
        int n = 0;
        while (true) {
            if (n >= m) {
                log.error("排队时间过长，下单失败");
            }
            n++;
            String url = String.format(api.getQueryOrderWaitTime(), System.currentTimeMillis(), myTicketInfoModel.getGlobalRepeatSubmitToken());
            HttpGet httpGet = new HttpGet(url);
            String response = httpUtil.get(httpGet);
            Gson jsonResult = new Gson();
            Map rsmap = jsonResult.fromJson(response, Map.class);
            if (rsmap.getOrDefault("status", "").toString().equals("true")) {
                rsmap = (Map<String, Object>) rsmap.get("data");
                String waitTime = rsmap.get("waitTime").toString();
                String waitCount = rsmap.get("waitCount").toString();
                String msg = rsmap.getOrDefault("msg", "").toString();
                String orderId = rsmap.get("orderId") == null ? null : rsmap.get("orderId").toString();
                log.info("等待获取订单号：前面" + waitCount + "人，需等待：" + waitTime + ",msg:" + msg);
                if (StringUtils.isEmpty(orderId)) {
                    int sleepTime = Double.valueOf(waitTime).intValue();
                    if (sleepTime < 0) {
                        return null;
                    }
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    log.info("下单成功，订单号：" + orderId + "，请尽快支付！！！");
                    return orderId;
                }
            }
        }
    }
}
