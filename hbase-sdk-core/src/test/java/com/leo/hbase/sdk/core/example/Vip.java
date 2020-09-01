package com.leo.hbase.sdk.core.example;


import com.leo.hbase.sdk.core.annotation.HBaseColumn;
import com.leo.hbase.sdk.core.annotation.HBaseTable;

/**
 * <p>Vip用户</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
@HBaseTable(schema = "TEST", name = "MY_VIP", uniqueFamily = "INFO")
public class Vip extends User {
    @HBaseColumn(name = "phone_no")
    private String phone;
    private boolean vip;
    @HBaseColumn(toUpperCase = true, family = "INFO2")
    private boolean isUseful;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public boolean isUseful() {
        return isUseful;
    }

    public void setUseful(boolean useful) {
        isUseful = useful;
    }
}
