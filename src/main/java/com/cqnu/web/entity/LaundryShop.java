package com.cqnu.web.entity;

import javax.persistence.*;

@Table(name = "laundry_shop")
public class LaundryShop {
    /**
     * 门店id
     */
    @Id
    @Column(name = "shop_id")
    private Integer shopId;

    /**
     * 门店编号
     */
    @Column(name = "shop_no")
    private Integer shopNo;

    @Column(name = "shop_category")
    private String shopCategory;

    /**
     * 门店名称
     */
    @Column(name = "shop_name")
    private String shopName;

    /**
     * 所属区域
     */
    @Column(name = "shop_area")
    private String shopArea;

    /**
     * 门店地址
     */
    @Column(name = "shop_address")
    private String shopAddress;

    /**
     * 门店负责人工号
     */
    @Column(name = "principal_no")
    private String principalNo;

    /**
     * 状态
     */
    private String state;

    /**
     * 获取门店id
     *
     * @return shop_id - 门店id
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * 设置门店id
     *
     * @param shopId 门店id
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取门店编号
     *
     * @return shop_no - 门店编号
     */
    public Integer getShopNo() {
        return shopNo;
    }

    /**
     * 设置门店编号
     *
     * @param shopNo 门店编号
     */
    public void setShopNo(Integer shopNo) {
        this.shopNo = shopNo;
    }

    /**
     * @return shop_category
     */
    public String getShopCategory() {
        return shopCategory;
    }

    /**
     * @param shopCategory
     */
    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    /**
     * 获取门店名称
     *
     * @return shop_name - 门店名称
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 设置门店名称
     *
     * @param shopName 门店名称
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 获取所属区域
     *
     * @return shop_area - 所属区域
     */
    public String getShopArea() {
        return shopArea;
    }

    /**
     * 设置所属区域
     *
     * @param shopArea 所属区域
     */
    public void setShopArea(String shopArea) {
        this.shopArea = shopArea;
    }

    /**
     * 获取门店地址
     *
     * @return shop_address - 门店地址
     */
    public String getShopAddress() {
        return shopAddress;
    }

    /**
     * 设置门店地址
     *
     * @param shopAddress 门店地址
     */
    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    /**
     * 获取门店负责人工号
     *
     * @return principal_no - 门店负责人工号
     */
    public String getPrincipalNo() {
        return principalNo;
    }

    /**
     * 设置门店负责人工号
     *
     * @param principalNo 门店负责人工号
     */
    public void setPrincipalNo(String principalNo) {
        this.principalNo = principalNo;
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