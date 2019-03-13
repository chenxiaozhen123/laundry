package com.cqnu.base.controller;

import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.web.entity.Admin;
import com.cqnu.web.model.AdminLoginInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 控制器
 * @Author xzchen
 * @Date 2019/2/23 14:59
 * @Version 1.0
 **/
public class BaseController {
    /**
     * 获取登录用户
     *
     * @param request
     * @return
     */
    protected Admin getLoginAdmin(HttpServletRequest request) {
        Object admin  = request.getSession().getAttribute(LaundryConsts.SESSION_USER_KEY);
        if(admin instanceof Admin){
            return (Admin) admin;
        }else {
            return null;
        }

    }


}
