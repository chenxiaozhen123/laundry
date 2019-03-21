package com.cqnu.web.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "customer")
public class Customer {
    /**
     * 编号
     */
    @Id
    @Column(name = "cust_id")
    private Integer custId;

    /**
     * 用户名
     */
    private String cname;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private String sex;

    /**
     * 电话号码
     */
    private String mobile;

    /**
     * 状态
     */
    private String state;

    /**
     * 生效时间
     */
    @Column(name = "eff_date")
    private Date effDate;

    /**
     * 获取编号
     *
     * @return cust_id - 编号
     */
    public Integer getCustId() {
        return custId;
    }

    /**
     * 设置编号
     *
     * @param custId 编号
     */
    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    /**
     * 获取用户名
     *
     * @return cname - 用户名
     */
    public String getCname() {
        return cname;
    }

    /**
     * 设置用户名
     *
     * @param cname 用户名
     */
    public void setCname(String cname) {
        this.cname = cname;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取性别
     *
     * @return sex - 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别
     *
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取电话号码
     *
     * @return mobile - 电话号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置电话号码
     *
     * @param mobile 电话号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    /**
     * 获取生效时间
     *
     * @return eff_date - 生效时间
     */
    public Date getEffDate() {
        return effDate;
    }

    /**
     * 设置生效时间
     *
     * @param effDate 生效时间
     */
    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }
}