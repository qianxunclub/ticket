package com.qianxunclub.ticket.util;

import com.qianxunclub.ticket.model.BuyTicketInfoModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * @author zhangbin
 * @date 2019-06-06 17:36
 * @description: TODO
 */
public class CommonUtil {

    public static String getThreadName(BuyTicketInfoModel buyTicketInfoModel) {
        String name = "" +
                "👤" + buyTicketInfoModel.getUsername() +
                "[" + buyTicketInfoModel.getRealName() +
                "-" + buyTicketInfoModel.getTrainNumber() + "]" +
                "-" + buyTicketInfoModel.getMobile();
        return name;
    }

    public static String getGMT(String date) {
        String str = "";
        TimeZone tz = TimeZone.getTimeZone("ETC/GMT-8");
        TimeZone.setDefault(tz);
        Calendar cal = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
        Date dd;
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            dd = shortSdf.parse(date);
            cal.setTime(dd);
            str = sdf.format(cal.getTime());
            return str + "+0800 (中国标准时间)";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
