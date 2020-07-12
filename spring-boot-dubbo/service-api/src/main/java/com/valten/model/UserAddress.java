package com.valten.support;

import java.io.Serializable;

public class UserAddress implements Serializable {

    private Integer id;
    private String address; // 用户地址
    private String userId; // 用户id
    private String name; // 收货人
    private String phone; // 电话号码
    private String isDefault; // 是否默认地址

    public UserAddress() {
    }

    public UserAddress(Integer id, String address, String userId, String name, String phone, String isDefault) {
        this.id = id;
        this.address = address;
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.isDefault = isDefault;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}
