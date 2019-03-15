package com.cqnu.web.controller;

import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.controller.BaseController;
import com.cqnu.web.entity.Admin;

import com.cqnu.web.model.AdminLoginInfo;
import com.cqnu.web.service.IRoleService;
import com.cqnu.web.service.ISysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 后台管理员登录
 * @Author xzchen
 * @Date 2019/3/12 12:32
 * @Version 1.0
 **/
@RestController
@RequestMapping("/admin/sys")
public class SysLoginController extends BaseController{
    @Autowired
    ISysLoginService sysLoginService;
    @Autowired
    IRoleService roleService;
    /**
     * 登陆
     */
    @ResponseBody
    @RequestMapping(value = "/login")
    public boolean login(HttpServletRequest request) {
        boolean flag = false;
        String username =  request.getParameter("username");
        String password =  request.getParameter("password");
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("admin_no",username);
        reqMap.put("password",password);
        Map<String, Object> resMap = sysLoginService.getAdmin(reqMap);
        if(null != resMap){
            flag = true;
            request.getSession().setAttribute(LaundryConsts.SESSION_USER_KEY,this.getAdmin(resMap));
        }
        return flag;
    }
    /**
     * 登录状态
     *
     * @param request
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/status")
    public AdminLoginInfo status(HttpServletRequest request) {
        AdminLoginInfo adminLoginInfo = new AdminLoginInfo();
        Admin admin = this.getLoginAdmin(request);
        if(null != admin){
            adminLoginInfo.setAdminNo(admin.getAdminNo());
            adminLoginInfo.setEmail(admin.getAdminEmail());
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("role_id",admin.getRoleId());
            Map<String, Object> resMap = roleService.getRole(reqMap);
            adminLoginInfo.setShopNo(admin.getShopNo());
            adminLoginInfo.setRoleId(admin.getRoleId());
            adminLoginInfo.setRolePriority(Integer.valueOf(resMap.get("role_priority").toString()));
            adminLoginInfo.setRoleName(resMap.get("role_name").toString());
            adminLoginInfo.setName(admin.getAdminName());
        }
        return adminLoginInfo;
    }
    /**
     * 退出登陆
     */
    @ResponseBody
    @RequestMapping(value = "/loginOut")
    public boolean loginOut(HttpServletRequest request) {
        request.getSession().removeAttribute(LaundryConsts.SESSION_USER_KEY);
        return true;
    }

    /**
     * 封装admin参数
     * @param map
     * @return
     */
    private Admin getAdmin(Map<String, Object> map){
        Admin admin = new Admin();
        admin.setAdminNo(map.get("admin_no").toString());
        admin.setAmdinPassword(map.get("amdin_password").toString());
        admin.setAdminId(Integer.valueOf(map.get("admin_id").toString()));
        admin.setAdminEmail(map.get("admin_email").toString());
        admin.setAdminName(map.get("admin_name").toString());
        admin.setAdminTelNum(map.get("admin_tel_num").toString());
        admin.setRoleId(Integer.valueOf(map.get("role_id").toString()));
        admin.setShopNo(Integer.valueOf(map.get("shop_no").toString()));
        admin.setState(map.get("state").toString());
        return admin;
    }
}
