package com.qianxunclub.ticket.service;

import com.google.gson.Gson;

import com.qianxunclub.ticket.config.ApiConfig;
import com.qianxunclub.ticket.ticket.Station;
import com.qianxunclub.ticket.model.PassengerModel;
import com.qianxunclub.ticket.model.BuyTicketInfoModel;
import com.qianxunclub.ticket.model.TicketModel;
import com.qianxunclub.ticket.model.UserModel;
import com.qianxunclub.ticket.model.UserTicketStore;
import com.qianxunclub.ticket.util.CommonUtil;
import com.qianxunclub.ticket.util.HttpUtil;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbin
 * @date 2019-05-30 16:17
 * @description: TODO
 */
@Slf4j
@Service
@AllArgsConstructor
public class ApiRequestService {

    private ApiConfig apiConfig;

    public Map<String, String> station() {
        HttpUtil httpUtil = new HttpUtil();
        Map<String, String> stationMap = new HashMap<>();
        HttpGet httpGet = new HttpGet(apiConfig.getStation());
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

    public void leftTicketInit(BuyTicketInfoModel buyTicketInfoModel) {
        HttpUtil httpUtil = UserTicketStore.httpUtilStore.get(buyTicketInfoModel.getUsername());
        String url = String.format(apiConfig.getInit() + "?linktypeid=dc");
        HttpGet httpGet = new HttpGet(url);
        String result = httpUtil.get(httpGet);
        String leftTicketBaseUrl = CommonUtil
                .regString("(?<=var CLeftTicketUrl = ').*?(?=')", result);
        if (!StringUtils.isEmpty(leftTicketBaseUrl)) {
            apiConfig.setLeftTicketBaseUrl("/otn/" + leftTicketBaseUrl);
        }
    }

    public List<TicketModel> queryTicket(BuyTicketInfoModel buyTicketInfoModel) {
        HttpUtil httpUtil = UserTicketStore.httpUtilStore.get(buyTicketInfoModel.getUsername());
        String url = String.format(apiConfig.getLeftTicket(), apiConfig.getLeftTicketBaseUrl(),
                buyTicketInfoModel.getDate(),
                Station.getCodeByName(buyTicketInfoModel.getFrom()),
                Station.getCodeByName(buyTicketInfoModel.getTo()));
        HttpGet httpGet = new HttpGet(url);
        String response = httpUtil.get(httpGet);
        if (StringUtils.isEmpty(response)) {
            log.error("车票查询失败!");
            return null;
        }
        Gson jsonResult = new Gson();
        Map<String, Object> rsmap = null;
        try {
            rsmap = jsonResult.fromJson(response, Map.class);
        } catch (Exception e) {
            log.error("车票查询失败!");
        }

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

    public boolean isLoginPassCode(String username) {
        HttpUtil httpUtil = UserTicketStore.httpUtilStore.get(username);
        HttpGet httpGet = new HttpGet(apiConfig.getLoginConfig());
        String response = httpUtil.get(httpGet);
        Gson jsonResult = new Gson();
        Map rsmap = jsonResult.fromJson(response, Map.class);
        Map data = (Map) rsmap.get("data");
        String isLoginPassCode = (String) data.get("is_login_passCode");
        return isLoginPassCode.equals("Y");
    }

    public String captchaImage(String userName) {
        HttpUtil httpUtil = UserTicketStore.httpUtilStore.get(userName);
        HttpGet httpGet = new HttpGet(String.format(apiConfig.getCaptchaImage(), Math.random()));
        String response = httpUtil.get(httpGet);
        Gson jsonResult = new Gson();
        Map rsmap = jsonResult.fromJson(response, Map.class);
        return (String) rsmap.get("image");
    }

    public boolean captchaCheck(String userName, String answer) {
        HttpUtil httpUtil = UserTicketStore.httpUtilStore.get(userName);
        HttpGet httpGet = new HttpGet(
                String.format(apiConfig.getCaptchaCheck(), answer, Math.random()));
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
        HttpUtil httpUtil = UserTicketStore.httpUtilStore.get(userModel.getUsername());
        HttpPost httpPost = new HttpPost(apiConfig.getUamtkStatic());
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("appid", "otn"));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams,
                Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
        String response = httpUtil.post(httpPost);
        Gson jsonResult = new Gson();
        Map rsmap = jsonResult.fromJson(response, Map.class);
        if ("0.0".equals(rsmap.get("result_code").toString())) {
            userModel.setUamtk(rsmap.get("newapptk").toString());
            UserTicketStore.httpUtilStore.put(userModel.getUsername(), httpUtil);
            return true;
        } else {
            return false;
        }
    }

    public boolean login(UserModel userModel) {
        HttpUtil httpUtil = UserTicketStore.httpUtilStore.get(userModel.getUsername());
        HttpPost httpPost = new HttpPost(apiConfig.getLogin());
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("username", userModel.getUsername()));
        formparams.add(new BasicNameValuePair("password", userModel.getPassword()));
        formparams.add(new BasicNameValuePair("appid", "otn"));
        formparams.add(new BasicNameValuePair("answer", userModel.getAnswer()));

        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams,
                Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
        String response = httpUtil.post(httpPost);
        if (StringUtils.isEmpty(response)) {
            log.error("登录失败!");
            return false;
        }
        Gson jsonResult = new Gson();
        Map rsmap = jsonResult.fromJson(response, Map.class);
        if (!"0.0".equals(rsmap.getOrDefault("result_code", "").toString())) {
            log.error("登陆失败：{}", rsmap);
            return false;
        }
        UserTicketStore.httpUtilStore.put(userModel.getUsername(), httpUtil);
        userModel.setUamtk(rsmap.get("uamtk").toString());
        return true;
    }

    public String uamtk(String userName) {
        HttpUtil httpUtil = UserTicketStore.httpUtilStore.get(userName);
        HttpPost httpPost = new HttpPost(apiConfig.getUamtk());
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("appid", "otn"));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams,
                Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
        Gson jsonResult = new Gson();
        String response = httpUtil.post(httpPost);
        Map rsmap = jsonResult.fromJson(response, Map.class);
        if ("0.0".equals(rsmap.getOrDefault("result_code", "").toString())) {
            UserTicketStore.httpUtilStore.put(userName, httpUtil);
            return rsmap.get("newapptk").toString();
        }
        return null;
    }

    public String uamauthclient(String userName, String tk) {
        HttpUtil httpUtil = UserTicketStore.httpUtilStore.get(userName);
        HttpPost httpPost = new HttpPost(apiConfig.getUamauthclient());
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("tk", tk));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams,
                Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
        Gson jsonResult = new Gson();
        String response = httpUtil.post(httpPost);
        Map rsmap = jsonResult.fromJson(response, Map.class);
        if ("0.0".equals(rsmap.getOrDefault("result_code", "").toString())) {
            UserTicketStore.httpUtilStore.put(userName, httpUtil);
            return rsmap.get("apptk").toString();
        }
        return null;
    }


    public List<PassengerModel> passengers(String userName) {
        HttpUtil httpUtil = UserTicketStore.httpUtilStore.get(userName);
        HttpPost httpPost = new HttpPost(apiConfig.getPassengers());
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("pageIndex", "1"));
        formparams.add(new BasicNameValuePair("pageSize", "100"));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams,
                Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
        Gson jsonResult = new Gson();
        String response = httpUtil.post(httpPost);
        Map rsmap = jsonResult.fromJson(response, Map.class);
        List<PassengerModel> passengerModelList = new ArrayList<>();
        if (null != rsmap.get("status") && rsmap.get("status").toString().equals("true")) {
            List<Map<String, String>> passengers = (List<Map<String, String>>) ((Map) rsmap
                    .get("data")).get("datas");
            if (!CollectionUtils.isEmpty(passengers)) {
                passengers.forEach(passenger -> {
                    PassengerModel passengerModel = new PassengerModel(passenger);
                    passengerModelList.add(passengerModel);
                });
            }
        }
        return passengerModelList;
    }

    public boolean checkUser(String userName) {
        HttpUtil httpUtil = UserTicketStore.httpUtilStore.get(userName);
        HttpPost httpPost = new HttpPost(apiConfig.getCheckUser());
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("_json_att", ""));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams,
                Consts.UTF_8);
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


    public boolean submitOrderRequest(BuyTicketInfoModel buyTicketInfoModel,
            TicketModel ticketModel) {
        HttpUtil httpUtil = UserTicketStore.httpUtilStore
                .get(buyTicketInfoModel.getUsername());
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        HttpPost httpPost = new HttpPost(apiConfig.getSubmitOrderRequest());
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("back_train_date", shortSdf.format(cal.getTime())));
        formparams.add(new BasicNameValuePair("purpose_codes", "ADULT"));
        formparams.add(new BasicNameValuePair("query_from_station_name",
                Station.getNameByCode(buyTicketInfoModel.getTo())));
        formparams.add(new BasicNameValuePair("query_to_station_name",
                Station.getNameByCode(buyTicketInfoModel.getFrom())));
        formparams.add(new BasicNameValuePair("secretStr", ticketModel.getSecret()));
        formparams.add(new BasicNameValuePair("train_date", buyTicketInfoModel.getDate()));
        formparams.add(new BasicNameValuePair("tour_flag", "dc"));
        formparams.add(new BasicNameValuePair("undefined", ""));

        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams,
                Consts.UTF_8);
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
        }
        return false;
    }

    public String initDc(String userName) {
        HttpUtil httpUtil = UserTicketStore.httpUtilStore.get(userName);
        String token = "";
        HttpGet httpGet = new HttpGet(apiConfig.getInitDc());
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


    public List<PassengerModel> getPassengerDTOs(String userName, String token) {
        HttpUtil httpUtil = UserTicketStore.httpUtilStore.get(userName);
        List<PassengerModel> passengerModelList = new ArrayList<>();
        HttpPost httpPost = new HttpPost(apiConfig.getGetPassengerDTOs());
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("_json_att", ""));
        formparams.add(new BasicNameValuePair("REPEAT_SUBMIT_TOKEN", token));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams,
                Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
        Gson jsonResult = new Gson();
        String response = httpUtil.post(httpPost);
        Map rsmap = jsonResult.fromJson(response, Map.class);
        if (rsmap.getOrDefault("status", "").toString().equals("true")) {
            rsmap = (Map<String, Object>) rsmap.get("data");
            List<Map<String, String>> passengers = (List<Map<String, String>>) rsmap
                    .get("normal_passengers");
            if (!CollectionUtils.isEmpty(passengers)) {
                passengers.forEach(passenger -> {
                    PassengerModel passengerModel = new PassengerModel(passenger);
                    passengerModelList.add(passengerModel);
                });
            }
        }
        return passengerModelList;
    }

    public boolean checkOrderInfo(BuyTicketInfoModel buyTicketInfoModel, TicketModel ticketModel) {
        HttpUtil httpUtil = UserTicketStore.httpUtilStore
                .get(buyTicketInfoModel.getUsername());
        HttpPost httpPost = new HttpPost(apiConfig.getCheckOrderInfo());
        List<NameValuePair> formparams = new ArrayList<>();

        formparams.add(new BasicNameValuePair("bed_level_order_num",
                "000000000000000000000000000000"));
        formparams.add(new BasicNameValuePair("cancel_flag", "2"));
        formparams.add(new BasicNameValuePair("whatsSelect", "2"));
        formparams.add(new BasicNameValuePair("_json_att", ""));
        formparams.add(new BasicNameValuePair("tour_flag", "dc"));
        formparams.add(new BasicNameValuePair("randCode", ""));
        formparams.add(new BasicNameValuePair("passengerTicketStr",
                buyTicketInfoModel.getPassengerModel()
                        .getPassengerTicketStr(buyTicketInfoModel, ticketModel)));
        formparams.add(new BasicNameValuePair("REPEAT_SUBMIT_TOKEN",
                buyTicketInfoModel.getGlobalRepeatSubmitToken()));
        formparams.add(new BasicNameValuePair("getOldPassengerStr",
                buyTicketInfoModel.getPassengerModel()
                        .getOldPassengerStr(buyTicketInfoModel.getPassengerModel())));

        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams,
                Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
        String response = httpUtil.post(httpPost);
        Gson jsonResult = new Gson();
        Map rsmap = jsonResult.fromJson(response, Map.class);
        if (rsmap.getOrDefault("status", "").toString().equals("true")) {
            rsmap = (Map<String, Object>) rsmap.get("data");
            if (rsmap.get("submitStatus").equals(false)) {
                log.error(rsmap.get("errMsg").toString());
                return false;
            }
            long ifShowPassCodeTime = Long.parseLong(rsmap.get("ifShowPassCodeTime").toString());
            try {
                Thread.sleep(ifShowPassCodeTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("开始提交订单");
            return true;
        }
        log.error("开始提交订单失败");
        return false;
    }

    public boolean checkRandCodeAnsyn(String position, String token) {
        HttpUtil httpUtil = new HttpUtil();
        HttpPost httpPost = new HttpPost(apiConfig.getCheckRandCodeAnsyn());
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("randCode", position));
        formparams.add(new BasicNameValuePair("REPEAT_SUBMIT_TOKEN", token));
        formparams.add(new BasicNameValuePair("rand", "randp"));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams,
                Consts.UTF_8);
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

    public boolean getQueueCount(BuyTicketInfoModel buyTicketInfoModel, TicketModel ticketModel) {
        HttpUtil httpUtil = UserTicketStore.httpUtilStore
                .get(buyTicketInfoModel.getUsername());
        HttpPost httpPost = new HttpPost(apiConfig.getGetQueueCount());
        List<NameValuePair> formparams = new ArrayList<>();

        formparams.add(new BasicNameValuePair("fromStationTelecode", ticketModel.getFrom()));
        formparams.add(new BasicNameValuePair("toStationTelecode", ticketModel.getTo()));
        formparams.add(new BasicNameValuePair("leftTicket", ticketModel.getLeftTicket()));
        formparams.add(new BasicNameValuePair("purpose_codes", "00"));
        formparams.add(new BasicNameValuePair("REPEAT_SUBMIT_TOKEN",
                buyTicketInfoModel.getGlobalRepeatSubmitToken()));
        formparams.add(new BasicNameValuePair("seatType",
                ticketModel.getSeat().get(0).getSeatLevel().getCode()));
        formparams.add(new BasicNameValuePair("stationTrainCode", ticketModel.getTrainNumber()));
        formparams.add(new BasicNameValuePair("train_date",
                CommonUtil.getGMT(ticketModel.getTrainDate())));
        formparams.add(new BasicNameValuePair("train_location", ticketModel.getTrainLocation()));
        formparams.add(new BasicNameValuePair("train_no", ticketModel.getTrainCode()));
        formparams.add(new BasicNameValuePair("_json_att", ""));

        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams,
                Consts.UTF_8);
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

    public boolean confirmSingleForQueue(BuyTicketInfoModel buyTicketInfoModel,
            TicketModel ticketModel) {
        HttpUtil httpUtil = UserTicketStore.httpUtilStore
                .get(buyTicketInfoModel.getUsername());
        HttpPost httpPost = new HttpPost(apiConfig.getConfirmSingleForQueue());

        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("dwAll", "N"));
        formparams.add(new BasicNameValuePair("purpose_codes", "00"));
        formparams.add(new BasicNameValuePair("key_check_isChange",
                buyTicketInfoModel.getKeyCheckIsChange()));
        formparams.add(new BasicNameValuePair("_json_att", ""));
        formparams.add(new BasicNameValuePair("leftTicketStr", ticketModel.getLeftTicket()));
        formparams.add(new BasicNameValuePair("train_location", ticketModel.getTrainLocation()));
        formparams.add(new BasicNameValuePair("choose_seats", ""));
        formparams.add(new BasicNameValuePair("whatsSelect", "1"));
        formparams.add(new BasicNameValuePair("roomType", "00"));
        formparams.add(new BasicNameValuePair("seatDetailType", "000"));
        formparams.add(new BasicNameValuePair("randCode", ""));
        formparams.add(new BasicNameValuePair("passengerTicketStr",
                buyTicketInfoModel.getPassengerModel()
                        .getPassengerTicketStr(buyTicketInfoModel, ticketModel)));
        formparams.add(new BasicNameValuePair("REPEAT_SUBMIT_TOKEN",
                buyTicketInfoModel.getGlobalRepeatSubmitToken()));
        formparams.add(new BasicNameValuePair("getOldPassengerStr",
                buyTicketInfoModel.getPassengerModel()
                        .getOldPassengerStr(buyTicketInfoModel.getPassengerModel())));

        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams,
                Consts.UTF_8);
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

    public String queryOrderWaitTime(BuyTicketInfoModel buyTicketInfoModel) {
        HttpUtil httpUtil = UserTicketStore.httpUtilStore
                .get(buyTicketInfoModel.getUsername());
        int m = 50;
        int n = 0;
        while (true) {
            if (n >= m) {
                log.error("排队时间过长，下单失败");
                return null;
            }
            n++;
            String url = String
                    .format(apiConfig.getQueryOrderWaitTime(), System.currentTimeMillis(),
                            buyTicketInfoModel.getGlobalRepeatSubmitToken());
            HttpGet httpGet = new HttpGet(url);
            String response = httpUtil.get(httpGet);
            Gson jsonResult = new Gson();
            Map rsmap = jsonResult.fromJson(response, Map.class);
            if (rsmap.getOrDefault("status", "").toString().equals("true")) {
                rsmap = (Map<String, Object>) rsmap.get("data");
                String waitTime = rsmap.get("waitTime").toString();
                String waitCount = rsmap.get("waitCount").toString();
                String msg = rsmap.getOrDefault("msg", "").toString();
                int sleepTime = Double.valueOf(waitTime).intValue();
                String orderId =
                        rsmap.get("orderId") == null ? null : rsmap.get("orderId").toString();

                if (!StringUtils.isEmpty(msg)) {
                    log.error(msg);
                }
                if (sleepTime == -100) {
                    log.error("获取订单出现-100错误。");
                }
                if (sleepTime == -2) {
                    return null;
                }

                if (sleepTime >= 0 && StringUtils.isEmpty(orderId)) {
                    log.info("正在等待获取订单号：预计前面" + waitCount + "人，预计需等待：" + waitTime);
                } else if (!StringUtils.isEmpty(orderId)) {
                    log.info("下单成功，订单号：" + orderId + "，请尽快支付！！！");
                    return orderId;
                } else {
                    log.error("无法等待获取订单号，正在尝试继续获取:{}", response);
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
