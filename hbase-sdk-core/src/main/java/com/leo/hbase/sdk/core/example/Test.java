package com.leo.hbase.sdk.core.example;

/**
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public class Test {
    public static final String constV = "hello";
    private boolean isVip;
    private Boolean costMoney;

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public Boolean getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(Boolean costMoney) {
        this.costMoney = costMoney;
    }
}
