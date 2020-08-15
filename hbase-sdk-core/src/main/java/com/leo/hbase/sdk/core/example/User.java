package com.leo.hbase.sdk.core.example;

/**
 * <p>自定义用户类</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public class User extends People {
    private String address;
    private String email;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
