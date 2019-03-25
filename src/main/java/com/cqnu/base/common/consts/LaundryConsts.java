package com.cqnu.base.common.consts;

import java.util.Hashtable;

/**
 * @Description 常量
 * @Author xzchen
 * @Date 2019/2/23 13:52
 * @Version 1.0
 **/
public class LaundryConsts {
    /**
     * 登录用户session标识
     */
    public static final String SESSION_USER_KEY = "LOGIN_USER";
    /**
     * 员工初始密码
     */
    public static final String INITIAL_PASSWORD= "123456";
    /**
     * 员工加/解密的密钥
     */
    public static final String WORKER_KEY="worker";
    /**
     * 顾客加/解密的密钥
     */
    public static final String CUSTOMER="customer";

    public static final String GENTLEMAN = "先生";
    public static final String MADAM = "女士";
    public static final String MAN = "男";
    public static final String WOMAN = "女";
    /**
     * 门店管理员初始工号
     */
    public static final String ADMIN_NO_LAUNDRY_SHOP = "2027000001";
    /**
     * 门店员工初始工号
     */
    public static final String ADMIN_NO_LAUNDRY = "6027000001";
    /**
     * 干洗中心管理员初始工号
     */
    public static final String ADMIN_NO_ADMIN = "1027000001";
    /**
     * 干洗中心员工初始工号
     */
    public static final String ADMIN_NO_WORKER = "5027000001";
    /**
     * 角色为门店员工
     */
    public static final String ROLE_ID_LAUNDRY = "3";
    /**
     * 角色为干洗中心员工
     */
    public static final String ROLE_ID_WORKER = "4";
    /**
     * 角色为门店管理员
     */
    public static final String ROLE_ID_LAUNDRY_SHOP = "2";
    /**
     * 超级管理员
     */
    public static final String ROLE_ID_ADMIN = "1";
    /**
     * 角色为干洗中心管理员
     */
    public static final String ROLE_ID_CENTER_ADMIN = "5";
    /**
     * 初始门店编号
     */
    public static final String SHOP_NO="1000000000";
    /**
     * 干洗中心门店编号+1
     */
    public static final String LAUNDRY_NO_INID="0";
    /**
     * 入职邮件主题
     */
    public static final String ENTRY_SUBJECT = "入职通知";
    /**
     * 入职邮件模板
     */
    public static final String ENTRY_TEMPLATE="accountMsg.ftl";
    /**
     *
     */
    public static final String TAKE_ACTION="取衣";
    public static final String SEND_ACTION="送洗";
    public static final String WASH_ACTION="清洗";
    public static final String HANG_ACTION="上挂";
    public static final String RECEIVE_ACTION="领取";
    public static final String TAKE_BACK_ACTION="取回";
    public static final String DELIVER_ACTION = "门店送衣/顾客取衣";

    public static final String WAIT_PAY_STATUS="待取衣";
    public static final String WAIT_TAKE_STATUS="待取衣";
    public static final String TAKE_STATUS="已取衣";
    public static final String SEND_STATUS="正在送洗";
    public static final String WASH_STATUS="正在清洗";
    public static final String HANG_STATUS="上挂";
    public static final String RECEIVE_STATUS="领取";
    public static final String TAKE_BACK_STATUS="取回";
    public static final String WAIT_CONFIRM_STATUS="待确认收衣";
    public static final String WAIT_REVIEW_STATUS="待评价";
    public static final String ORDER_OVER = "已完成";
    /**
     * 初始分类编号
     */
    public static final String CAT_NO="10000";
    /**
     * 初始商品编号
     */
    public static final String GOODS_NO = "1000000";
    /**
     * 邮箱验证主题
     */
    public static final String CAPTCHA_EMAIL_SUBJECT = "邮箱验证";
    /**
     * 邮箱验证模板
     */
    public static final String CAPTCHA_EMAIL = "emailCaptcha.ftl";
    public static final Hashtable REVIEW_RATE_DESC = new Hashtable();
    static {
        REVIEW_RATE_DESC.put("0", "好评");
        REVIEW_RATE_DESC.put("1", "中评");
        REVIEW_RATE_DESC.put("2", "差评");

    }
}