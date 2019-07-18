package com.qianxunclub.ticket.constant;

import lombok.Getter;

/**
 * @author zhangbin
 * @date 2019-05-31 10:52
 * @description: TODO
 */
@Getter
public enum SeatLevelEnum {

    YINGWO("硬卧",28,"3"),
    RUANWO("软卧",23,"4"),
    TWO("二等座",30,"O"),
    RUANZUO("软座",24,"2"),
    YINGZUO("硬座",29,"1"),
    ONE("一等座",31,"M"),
    SHANGWUZUO("商务座",32,"9"),
    GAOJIRUANWO("高级软卧",21,"6"),
    WUZUO("无座",26,"1")
    ;

    private String name;
    private int index;
    private String code;

    SeatLevelEnum(String name,int index,String code) {
        this.name = name;
        this.index = index;
        this.code = code;
    }
}
