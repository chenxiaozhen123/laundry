package com.cqnu.web.entity;

import javax.persistence.*;

@Table(name = "address")
public class Address {
    /**
     * 地址编号
     */
    @Id
    @Column(name = "address_id")
    private Integer addressId;

    /**
     * 顾客编号
     */
    @Column(name = "cust_id")
    private Integer custId;

    /**
     * 收货人姓名
     */
    private String recevier;

    /**
     * 收货人手机号码
     */
    private String mobile;

    /**
     * 地址
     */
    private String address;

    /**
     * 状态
     */
    private String state;

    /**
     * 获取地址编号
     *
     * @return address_id - 地址编号
     */
    public Integer getAddressId() {
        return addressId;
    }

    /**
     * 设置地址编号
     *
     * @param addressId 地址编号
     */
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    /**
     * 获取顾客编号
     *
     * @return cust_id - 顾客编号
     */
    public Integer getCustId() {
        return custId;
    }

    /**
     * 设置顾客编号
     *
     * @param custId 顾客编号
     */
    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    /**
     * 获取收货人姓名
     *
     * @return recevier - 收货人姓名
     */
    public String getRecevier() {
        return recevier;
    }

    /**
     * 设置收货人姓名
     *
     * @param recevier 收货人姓名
     */
    public void setRecevier(String recevier) {
        this.recevier = recevier;
    }

    /**
     * 获取收货人手机号码
     *
     * @return mobile - 收货人手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置收货人手机号码
     *
     * @param mobile 收货人手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取地址
     *
     * @return address - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取状态
     *
     * @return state - 状态
     */
    public String getState() {
        return state;
    }

    /**
     * 设置状态
     *
     * @param state 状态
     */
    public void setState(String state) {
        this.state = state;
    }
}