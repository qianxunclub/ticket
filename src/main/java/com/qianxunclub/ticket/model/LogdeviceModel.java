package com.qianxunclub.ticket.model;


/**
 * @author zhangbin
 * @date 2019-05-30 16:46
 * @description: TODO
 */
public class LogdeviceModel {

    private String exp;
    private String dfp;

    public LogdeviceModel(String exp,String dfp){
        this.exp = exp;
        this.dfp = dfp;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getDfp() {
        return dfp;
    }

    public void setDfp(String dfp) {
        this.dfp = dfp;
    }
}
