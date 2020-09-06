package com.github.CCweixiao.entity;

import com.github.CCweixiao.annotation.HBaseColumn;
import com.github.CCweixiao.annotation.HBaseRowKey;
import com.github.CCweixiao.annotation.HBaseTable;

import java.util.List;
import java.util.Map;

/**
 * <p>测试用户实体类</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 * @version 1.0
 * @organization bigdata
 * @website https://www.jielongping.com
 * @date 2020/6/29 11:09 上午
 * @since 1.0
 */
@HBaseTable(schema = "TEST", name = "LEO_USER", uniqueFamily = "info1")
public class UserEntity extends ModelEntity{
    @HBaseRowKey
    private String userId;

    private String username;
    private int age;
    private List<String> addresses;
    private Map<String,Object> contactInfo;
    private Double pay;

    @HBaseColumn(name = "is_vip", family = "INFO2", toUpperCase = true)
    private boolean isVip;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public Map<String, Object> getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(Map<String, Object> contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Double getPay() {
        return pay;
    }

    public void setPay(Double pay) {
        this.pay = pay;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", addresses=" + addresses +
                ", contactInfo=" + contactInfo +
                ", pay=" + pay +
                ", isVip=" + isVip +
                '}';
    }
}
