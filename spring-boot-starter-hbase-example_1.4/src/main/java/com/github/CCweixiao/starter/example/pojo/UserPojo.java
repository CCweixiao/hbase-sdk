//package com.github.CCweixiao.starter.example.pojo;
//
//
//import com.github.CCweixiao.annotation.HBaseColumn;
//import com.github.CCweixiao.annotation.HBaseTable;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
///**
// * <p>用户数据绑定类</p>
// *
// * @author leo.jie (leojie1314@gmail.com)
// */
//@HBaseTable(schema = "test", name = "user", uniqueFamily = "info")
//public class UserPojo extends PeoplePojo {
//    @HBaseColumn(name = "userName")
//    private String username;
//    private int age;
//    @HBaseColumn(name = "pay", family = "info2", toUpperCase = true)
//    private float pay;
//    private List<String> address;
//    private double cost;
//    private long timestamp;
//    private Date createDate;
//    private Map<String, Object> info;
//    private BigDecimal bigDecimal;
//    private String[] roles;
//    @HBaseColumn(name = "is_vip", family = "info", toUpperCase = false)
//    private boolean isVip;
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public float getPay() {
//        return pay;
//    }
//
//    public void setPay(float pay) {
//        this.pay = pay;
//    }
//
//    public List<String> getAddress() {
//        return address;
//    }
//
//    public void setAddress(List<String> address) {
//        this.address = address;
//    }
//
//    public double getCost() {
//        return cost;
//    }
//
//    public void setCost(double cost) {
//        this.cost = cost;
//    }
//
//    public long getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(long timestamp) {
//        this.timestamp = timestamp;
//    }
//
//    public Date getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(Date createDate) {
//        this.createDate = createDate;
//    }
//
//    public Map<String, Object> getInfo() {
//        return info;
//    }
//
//    public void setInfo(Map<String, Object> info) {
//        this.info = info;
//    }
//
//    public BigDecimal getBigDecimal() {
//        return bigDecimal;
//    }
//
//    public void setBigDecimal(BigDecimal bigDecimal) {
//        this.bigDecimal = bigDecimal;
//    }
//
//    public String[] getRoles() {
//        return roles;
//    }
//
//    public void setRoles(String[] roles) {
//        this.roles = roles;
//    }
//
//    public boolean isVip() {
//        return isVip;
//    }
//
//    public void setVip(boolean vip) {
//        isVip = vip;
//    }
//}
