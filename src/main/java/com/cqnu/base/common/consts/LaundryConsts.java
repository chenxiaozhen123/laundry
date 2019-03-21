package com.cqnu.base.common.consts;

/**
 * @Description 常量
 * @Author xzchen
 * @Date 2019/2/23 13:52
 * @Version 1.0
 **/
public interface LaundryConsts {
    /**
     * 登录用户session标识
     */
    String SESSION_USER_KEY = "LOGIN_USER";
    /**
     * 员工初始密码
     */
    String INITIAL_PASSWORD= "123456";
    /**
     * 员工加/解密的密钥
     */
    String WORKER_KEY="worker";
    /**
     * 顾客加/解密的密钥
     */
    String CUSTOMER="customer";

    String GENTLEMAN = "先生";
    String MADAM = "女士";
    String MAN = "男";
    String WOMAN = "女";
    /**
     * 门店管理员初始工号
     */
    String ADMIN_NO_LAUNDRY_SHOP = "2027000001";
    /**
     * 门店员工初始工号
     */
    String ADMIN_NO_LAUNDRY = "6027000001";
    /**
     * 干洗中心管理员初始工号
     */
    String ADMIN_NO_ADMIN = "1027000001";
    /**
     * 干洗中心员工初始工号
     */
    String ADMIN_NO_WORKER = "5027000001";
    /**
     * 角色为门店员工
     */
    String ROLE_ID_LAUNDRY = "3";
    /**
     * 角色为干洗中心员工
     */
    String ROLE_ID_WORKER = "4";
    /**
     * 角色为门店管理员
     */
    String ROLE_ID_LAUNDRY_SHOP = "2";
    /**
     * 超级管理员
     */
    String ROLE_ID_ADMIN = "1";
    /**
     * 角色为干洗中心管理员
     */
    String ROLE_ID_CENTER_ADMIN = "5";
    /**
     * 初始门店编号
     */
    String SHOP_NO="1000000000";
    /**
     * 干洗中心门店编号+1
     */
    String LAUNDRY_NO_INID="0";
    /**
     * 入职邮件主题
     */
    String ENTRY_SUBJECT = "入职通知";
    /**
     * 入职邮件模板
     */
    String ENTRY_TEMPLATE="accountMsg.ftl";
    /**
     *
     */
    String TAKE_ACTION="取衣";
    String SEND_ACTION="送洗";
    String WASH_ACTION="清洗";
    String HANG_ACTION="上挂";
    String RECEIVE_ACTION="领取";
    String TAKE_BACK_ACTION="取回";
    String DELIVER_ACTION = "门店送衣/顾客取衣";

    String WAIT_TAKE_STATUS="待取衣";
    String TAKE_STATUS="已取衣";
    String SEND_STATUS="正在送洗";
    String WASH_STATUS="正在清洗";
    String HANG_STATUS="上挂";
    String RECEIVE_STATUS="领取";
    String TAKE_BACK_STATUS="取回";
    String WAIT_CONFIRM_STATUS="待确认收衣";
    String WAIT_REVIEW_STATUS="待评价";
    /**
     * 初始分类编号
     */
    String CAT_NO="10000";
    /**
     * 初始商品编号
     */
    String GOODS_NO = "1000000";

}