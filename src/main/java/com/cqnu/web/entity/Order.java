package com.cqnu.web.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "order")
public class Order {
    /**
     * 订单编号
     */
    @Id
    @Column(name = "order_id")
    private String orderId;

    /**
     * 地址
     */
    @Column(name = "addressId")
    private String addressid;

    /**
     * 状态
     */
    private String status;

    /**
     * 所属门店编号
     */
    @Column(name = "shopNo")
    private Integer shopno;

    /**
     * 预约取衣时间
     */
    @Column(name = "appointDate")
    private Date appointdate;

    /**
     * 金额
     */
    private Double price;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "createDate")
    private Date createdate;

    /**
     * 支付时间
     */
    @Column(name = "payDate")
    private Date paydate;

    /**
     * 取衣时间
     */
    @Column(name = "takeDate")
    private Date takedate;

    /**
     * 送洗时间
     */
    @Column(name = "sendDate")
    private Date senddate;

    /**
     * 清洗时间
     */
    @Column(name = "washDate")
    private Date washdate;

    /**
     * 上挂时间
     */
    @Column(name = "hangDate")
    private Date hangdate;

    /**
     * 领取时间
     */
    @Column(name = "receiveDate")
    private Date receivedate;

    /**
     * 取回时间
     */
    @Column(name = "takeBackDate")
    private Date takebackdate;

    /**
     * 门店送衣/顾客自取衣时间
     */
    @Column(name = "deliverDate")
    private Date deliverdate;

    /**
     * 顾客确认收衣时间
     */
    @Column(name = "confirmDate")
    private Date confirmdate;

    /**
     * 评价时间
     */
    @Column(name = "reviewDate")
    private Date reviewdate;

    /**
     * 状态
     */
    private String state;

    /**
     * 获取订单编号
     *
     * @return order_id - 订单编号
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单编号
     *
     * @param orderId 订单编号
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取地址
     *
     * @return addressId - 地址
     */
    public String getAddressid() {
        return addressid;
    }

    /**
     * 设置地址
     *
     * @param addressid 地址
     */
    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取所属门店编号
     *
     * @return shopNo - 所属门店编号
     */
    public Integer getShopno() {
        return shopno;
    }

    /**
     * 设置所属门店编号
     *
     * @param shopno 所属门店编号
     */
    public void setShopno(Integer shopno) {
        this.shopno = shopno;
    }

    /**
     * 获取预约取衣时间
     *
     * @return appointDate - 预约取衣时间
     */
    public Date getAppointdate() {
        return appointdate;
    }

    /**
     * 设置预约取衣时间
     *
     * @param appointdate 预约取衣时间
     */
    public void setAppointdate(Date appointdate) {
        this.appointdate = appointdate;
    }

    /**
     * 获取金额
     *
     * @return price - 金额
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 设置金额
     *
     * @param price 金额
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取创建时间
     *
     * @return createDate - 创建时间
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * 设置创建时间
     *
     * @param createdate 创建时间
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    /**
     * 获取支付时间
     *
     * @return payDate - 支付时间
     */
    public Date getPaydate() {
        return paydate;
    }

    /**
     * 设置支付时间
     *
     * @param paydate 支付时间
     */
    public void setPaydate(Date paydate) {
        this.paydate = paydate;
    }

    /**
     * 获取取衣时间
     *
     * @return takeDate - 取衣时间
     */
    public Date getTakedate() {
        return takedate;
    }

    /**
     * 设置取衣时间
     *
     * @param takedate 取衣时间
     */
    public void setTakedate(Date takedate) {
        this.takedate = takedate;
    }

    /**
     * 获取送洗时间
     *
     * @return sendDate - 送洗时间
     */
    public Date getSenddate() {
        return senddate;
    }

    /**
     * 设置送洗时间
     *
     * @param senddate 送洗时间
     */
    public void setSenddate(Date senddate) {
        this.senddate = senddate;
    }

    /**
     * 获取清洗时间
     *
     * @return washDate - 清洗时间
     */
    public Date getWashdate() {
        return washdate;
    }

    /**
     * 设置清洗时间
     *
     * @param washdate 清洗时间
     */
    public void setWashdate(Date washdate) {
        this.washdate = washdate;
    }

    /**
     * 获取上挂时间
     *
     * @return hangDate - 上挂时间
     */
    public Date getHangdate() {
        return hangdate;
    }

    /**
     * 设置上挂时间
     *
     * @param hangdate 上挂时间
     */
    public void setHangdate(Date hangdate) {
        this.hangdate = hangdate;
    }

    /**
     * 获取领取时间
     *
     * @return receiveDate - 领取时间
     */
    public Date getReceivedate() {
        return receivedate;
    }

    /**
     * 设置领取时间
     *
     * @param receivedate 领取时间
     */
    public void setReceivedate(Date receivedate) {
        this.receivedate = receivedate;
    }

    /**
     * 获取取回时间
     *
     * @return takeBackDate - 取回时间
     */
    public Date getTakebackdate() {
        return takebackdate;
    }

    /**
     * 设置取回时间
     *
     * @param takebackdate 取回时间
     */
    public void setTakebackdate(Date takebackdate) {
        this.takebackdate = takebackdate;
    }

    /**
     * 获取门店送衣/顾客自取衣时间
     *
     * @return deliverDate - 门店送衣/顾客自取衣时间
     */
    public Date getDeliverdate() {
        return deliverdate;
    }

    /**
     * 设置门店送衣/顾客自取衣时间
     *
     * @param deliverdate 门店送衣/顾客自取衣时间
     */
    public void setDeliverdate(Date deliverdate) {
        this.deliverdate = deliverdate;
    }

    /**
     * 获取顾客确认收衣时间
     *
     * @return confirmDate - 顾客确认收衣时间
     */
    public Date getConfirmdate() {
        return confirmdate;
    }

    /**
     * 设置顾客确认收衣时间
     *
     * @param confirmdate 顾客确认收衣时间
     */
    public void setConfirmdate(Date confirmdate) {
        this.confirmdate = confirmdate;
    }

    /**
     * 获取评价时间
     *
     * @return reviewDate - 评价时间
     */
    public Date getReviewdate() {
        return reviewdate;
    }

    /**
     * 设置评价时间
     *
     * @param reviewdate 评价时间
     */
    public void setReviewdate(Date reviewdate) {
        this.reviewdate = reviewdate;
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