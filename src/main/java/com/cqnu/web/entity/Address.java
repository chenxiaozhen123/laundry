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