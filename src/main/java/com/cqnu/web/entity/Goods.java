package com.cqnu.web.entity;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "goods")
public class Goods {
    /**
     * 商品编号
     */
    @Id
    @Column(name = "goods_no")
    private String goodsNo;

    /**
     * 分类编号
     */
    @Column(name = "cat_no")
    private String catNo;

    @Column(name = "goods_name")
    private String goodsName;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 图片路径
     */
    @Column(name = "img_path")
    private String imgPath;

    private String state;

    /**
     * 获取商品编号
     *
     * @return goods_no - 商品编号
     */
    public String getGoodsNo() {
        return goodsNo;
    }

    /**
     * 设置商品编号
     *
     * @param goodsNo 商品编号
     */
    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    /**
     * 获取分类编号
     *
     * @return cat_no - 分类编号
     */
    public String getCatNo() {
        return catNo;
    }

    /**
     * 设置分类编号
     *
     * @param catNo 分类编号
     */
    public void setCatNo(String catNo) {
        this.catNo = catNo;
    }

    /**
     * @return goods_name
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * @param goodsName
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * 获取价格
     *
     * @return price - 价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置价格
     *
     * @param price 价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取图片路径
     *
     * @return img_path - 图片路径
     */
    public String getImgPath() {
        return imgPath;
    }

    /**
     * 设置图片路径
     *
     * @param imgPath 图片路径
     */
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    /**
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }
}