package com.dashubio.model.ucenter;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 设备用户
 */
public class User implements Serializable{

    public final static int NO_SEX = 0;//无
    public final static int MALE = 1;//男
    public final static int FEMALE = 2;//女

    @SerializedName("id")
    private String userid;

    private String username;

    private String phone;

    @SerializedName("name")
    private String fullname;//姓名

    private String password;

    private String t_session;

    private int sex;

    private String company;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getT_session() {
        return t_session;
    }

    public void setT_session(String t_session) {
        this.t_session = t_session;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
