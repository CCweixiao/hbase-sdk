package com.github.CCweixiao.entity;

import com.github.CCweixiao.annotation.HBaseColumn;
import com.github.CCweixiao.annotation.HBaseRowKey;
import com.github.CCweixiao.annotation.HBaseTable;

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
public class UserEntity {
    @HBaseRowKey
    private String userId;
    private String username;
    private int age;
    @HBaseColumn(name = "is_vip", family = "INFO2", toUpperCase = true)
    private boolean isVip;
    private boolean isDisabled;

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

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", isVip=" + isVip +
                '}';
    }
}
