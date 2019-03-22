package com.cqnu.web.entity;

import javax.persistence.*;

@Table(name = "category")
public class Category {
    /**
     * 分类编号
     */
    @Column(name = "cat_no")
    private String catNo;

    /**
     * 分类名称
     */
    @Column(name = "cat_name")
    private String catName;

    @Column(name = "img_path")
    private String imgPath;

    /**
     * 状态
     */
    private String state;

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
     * 获取分类名称
     *
     * @return cat_name - 分类名称
     */
    public String getCatName() {
        return catName;
    }

    /**
     * 设置分类名称
     *
     * @param catName 分类名称
     */
    public void setCatName(String catName) {
        this.catName = catName;
    }

    /**
     * @return img_path
     */
    public String getImgPath() {
        return imgPath;
    }

    /**
     * @param imgPath
     */
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
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