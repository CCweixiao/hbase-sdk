package com.leo.hbase.sdk.core.example;

/**
 * <p>TODO</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 * @version 1.0
 * @organization bigdata
 * @website https://www.jielongping.com
 * @date 2020/6/22 6:47 下午
 * @since 1.0
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
