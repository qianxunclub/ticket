package com.qianxunclub.ticket.ticket;


import com.qianxunclub.ticket.service.ApiRequestService;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


/**
 * @author zhangbin
 * @date 2019-05-31 10:11
 * @description: TODO
 */
@Component
public class Station {

    private static Map<String, String> stations;

    public void load(ApiRequestService apiRequestService){
        stations = apiRequestService.station();
    }


    public static String getCodeByName(String name){
        return stations.get(name);
    }

    public static String getNameByCode(String c){
        AtomicReference<String> result = new AtomicReference<>("");
        stations.forEach((name, code) -> {
            if(code.equals(c)){
                result.set(name);
            }
        });
        return result.get();
    }

}
