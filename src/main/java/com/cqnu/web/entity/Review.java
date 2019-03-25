package com.cqnu.web.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "review")
public class Review {
    @Id
    private Integer id;

    /**
     * 顾客编号
     */
    @Column(name = "cust_id")
    private Integer custId;

    @Column(name = "order_id")
    private Integer orderId;

    /**
     * 评价等级
     */
    private String rate;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 评价时间
     */
    @Column(name = "createDate")
    private Date createdate;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
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
     * @return order_id
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取评价等级
     *
     * @return rate - 评价等级
     */
    public String getRate() {
        return rate;
    }

    /**
     * 设置评价等级
     *
     * @param rate 评价等级
     */
    public void setRate(String rate) {
        this.rate = rate;
    }

    /**
     * 获取评价内容
     *
     * @return content - 评价内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置评价内容
     *
     * @param content 评价内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取评价时间
     *
     * @return createDate - 评价时间
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * 设置评价时间
     *
     * @param createdate 评价时间
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}