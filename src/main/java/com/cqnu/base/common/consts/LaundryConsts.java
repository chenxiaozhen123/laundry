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
     * 初始密码
     */
    String INITIAL_PASSWORD= "123456";

    String GENTLEMAN = "先生";
    String MADAM = "女士";
    /**
     * 门店管理员初始工号
     */
    String ADMIN_NO_LAUNDRY_SHOP = "1027000001";
    /**
     * 门店员工初始工号
     */
    String ADMIN_NO_LAUNDRY = "6027000001";
    /**
     * 干洗中心员工初始工号
     */
    String ADMIN_NO_WORKER = "3027000001";
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
     * 初始门店编号
     */
    String SHOP_NO="1000000000";
    /**
     * 干洗中心门店编号+1
     */
    String LAUNDRY_NO_INID="0";

}
