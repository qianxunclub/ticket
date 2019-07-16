package com.qianxunclub.ticket.util;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author zhangbin
 * @date 2019-07-01 12:10
 * @description: TODO
 */
public class LogUtil {
    private static ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

    public static String getLog(){
        return concurrentLinkedQueue.poll();
    }

    public static void push(String log){
        concurrentLinkedQueue.add(log);
    }
}
