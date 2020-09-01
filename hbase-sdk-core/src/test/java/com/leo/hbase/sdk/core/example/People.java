package com.leo.hbase.sdk.core.example;

/**
 * <p>描述人的数据模型类</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public class People {
    private int id;
    private String username;
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
