package com.jonssonyan.entity;

/**
 * 用户的实体
 */
public class User {
    //用户名
    private String username;
    //IP地址
    private String ip;

    public User(String userDescription) {
        //把字符串用%分割
        String[] items = userDescription.split("%");
        //第一部分赋给用户名
        this.username = items[0];
        //第二部分赋给IP地址
        this.ip = items[1];
    }

    // 构造方法
    public User() {
    }

    public User(String username, String ip) {
        this.username = username;
        this.ip = ip;
    }

    // get set
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    //统一用 “用户名” + “%” + “IP地址” 的形式表示
    public String description() {
        return username + "%" + ip;
    }
}
