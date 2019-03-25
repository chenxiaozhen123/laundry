package com.cqnu.web.controller;

import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.controller.BaseController;
import com.cqnu.base.model.BaseRes;
import com.cqnu.base.util.AESUtil;
import com.cqnu.web.entity.Admin;

import com.cqnu.web.model.AdminLoginInfo;
import com.cqnu.web.service.ILaundryShopService;
import com.cqnu.web.service.IRoleService;
import com.cqnu.web.service.ISysLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    private static Logger logger = LoggerFactory.getLogger(SysLoginController.class);
    private static String calssPath = "com.cqnu.web.controller.SysLoginController";
    @Autowired
    ISysLoginService sysLoginService;
    @Autowired
    IRoleService roleService;
    @Autowired
    ILaundryShopService laundryShopService;
    /**
     * 登陆
     */
    @ResponseBody
    @RequestMapping(value = "/login")
    public BaseRes login(HttpServletRequest request) {
        try{
            String username =  request.getParameter("username");
            String password =  request.getParameter("password");
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("admin_no",username);
            reqMap.put("password",AESUtil.aesEncrypt(password,LaundryConsts.WORKER_KEY));
            Map<String, Object> resMap = sysLoginService.getAdmin(reqMap);
            if(null != resMap){
                request.getSession().setAttribute(LaundryConsts.SESSION_USER_KEY,this.getAdmin(resMap));
            }else{
                return BaseRes.getFailure("用户名或密码错误");
            }
        }catch (DataAccessException e){
            return BaseRes.getException("数据库操作异常");
        }catch (Exception e){
            return BaseRes.getException("登录失败");
        }
        return BaseRes.getSuccess();
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
            reqMap = new HashMap<>();
            reqMap.put("shop_no",admin.getShopNo());
            Map<String, Object> resShopMap = laundryShopService.getShopByShopNo(reqMap);
            if(null != resShopMap){
                adminLoginInfo.setShopName(resShopMap.get("shop_name").toString());
            }else {
                adminLoginInfo.setShopName("");
            }
            if(null != resMap){
                adminLoginInfo.setRoleName(resMap.get("role_name").toString());
            }else {
                adminLoginInfo.setRoleName("");
            }
            adminLoginInfo.setShopNo(admin.getShopNo());
            adminLoginInfo.setRoleId(admin.getRoleId());
            adminLoginInfo.setRolePriority(Integer.valueOf(resMap.get("role_priority").toString()));
            adminLoginInfo.setName(admin.getAdminName());
            adminLoginInfo.setSex(admin.getAdminSex());

            adminLoginInfo.setTelNum(admin.getAdminTelNum());
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
        admin.setAdminPassword(map.get("admin_password").toString());
        admin.setAdminId(Integer.valueOf(map.get("admin_id").toString()));
        admin.setAdminEmail(map.get("admin_email").toString());
        admin.setAdminSex(map.get("admin_sex").toString());
        admin.setAdminName(map.get("admin_name").toString());
        admin.setAdminTelNum(map.get("admin_tel_num").toString());
        admin.setRoleId(Integer.valueOf(map.get("role_id").toString()));
        admin.setShopNo(Integer.valueOf(map.get("shop_no").toString()));
        admin.setState(map.get("state").toString());
        return admin;
    }
}
