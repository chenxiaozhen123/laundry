package com.cqnu.web.entity;

import javax.persistence.*;

@Table(name = "admin")
public class Admin {
    /**
     * 管理员id
     */
    @Id
    @Column(name = "admin_id")
    private Integer adminId;

    /**
     * 工号
     */
    @Column(name = "admin_no")
    private String adminNo;

    /**
     * 名字
     */
    @Column(name = "admin_name")
    private String adminName;

    /**
     * 密码
     */
    @Column(name = "amdin_password")
    private String amdinPassword;

    /**
     * 手机号
     */
    @Column(name = "admin_tel_num")
    private String adminTelNum;

    /**
     * 邮箱
     */
    @Column(name = "admin_email")
    private String adminEmail;

    /**
     * 性别
     */
    @Column(name = "admin_sex")
    private String adminSex;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 所属门店
     */
    @Column(name = "shop_no")
    private Integer shopNo;

    /**
     * 状态
     */
    private String state;

    /**
     * 获取管理员id
     *
     * @return admin_id - 管理员id
     */
    public Integer getAdminId() {
        return adminId;
    }

    /**
     * 设置管理员id
     *
     * @param adminId 管理员id
     */
    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    /**
     * 获取工号
     *
     * @return admin_no - 工号
     */
    public String getAdminNo() {
        return adminNo;
    }

    /**
     * 设置工号
     *
     * @param adminNo 工号
     */
    public void setAdminNo(String adminNo) {
        this.adminNo = adminNo;
    }

    /**
     * 获取名字
     *
     * @return admin_name - 名字
     */
    public String getAdminName() {
        return adminName;
    }

    /**
     * 设置名字
     *
     * @param adminName 名字
     */
    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    /**
     * 获取密码
     *
     * @return amdin_password - 密码
     */
    public String getAmdinPassword() {
        return amdinPassword;
    }

    /**
     * 设置密码
     *
     * @param amdinPassword 密码
     */
    public void setAmdinPassword(String amdinPassword) {
        this.amdinPassword = amdinPassword;
    }

    /**
     * 获取手机号
     *
     * @return admin_tel_num - 手机号
     */
    public String getAdminTelNum() {
        return adminTelNum;
    }

    /**
     * 设置手机号
     *
     * @param adminTelNum 手机号
     */
    public void setAdminTelNum(String adminTelNum) {
        this.adminTelNum = adminTelNum;
    }

    /**
     * 获取邮箱
     *
     * @return admin_email - 邮箱
     */
    public String getAdminEmail() {
        return adminEmail;
    }

    /**
     * 设置邮箱
     *
     * @param adminEmail 邮箱
     */
    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    /**
     * 获取性别
     *
     * @return admin_sex - 性别
     */
    public String getAdminSex() {
        return adminSex;
    }

    /**
     * 设置性别
     *
     * @param adminSex 性别
     */
    public void setAdminSex(String adminSex) {
        this.adminSex = adminSex;
    }

    /**
     * 获取角色id
     *
     * @return role_id - 角色id
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置角色id
     *
     * @param roleId 角色id
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取所属门店
     *
     * @return shop_no - 所属门店
     */
    public Integer getShopNo() {
        return shopNo;
    }

    /**
     * 设置所属门店
     *
     * @param shopNo 所属门店
     */
    public void setShopNo(Integer shopNo) {
        this.shopNo = shopNo;
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