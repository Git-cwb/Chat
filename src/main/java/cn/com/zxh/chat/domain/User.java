package cn.com.zxh.chat.domain;

import java.io.Serializable;

///用户类
public class User implements Serializable {
    private Integer userId;     //用户id
    private String userName;    //用户名
    private String role;        //角色  0：客服   1：企业  2：个人

    public User() {
    }

    public User(Integer userId, String userName, String role) {
        this.userId = userId;
        this.userName = userName;
        this.role = role;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
