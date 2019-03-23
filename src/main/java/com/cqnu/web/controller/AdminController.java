package com.cqnu.web.controller;

import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.common.exception.LaundryException;
import com.cqnu.base.model.BaseRes;
import com.cqnu.base.model.Message;
import com.cqnu.base.service.BaseService;
import com.cqnu.base.util.AESUtil;
import com.cqnu.base.util.MailUtil;
import com.cqnu.web.service.IAdminService;
import com.cqnu.web.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 员工管理
 * @Author xzchen
 * @Date 2019/3/14 19:54
 * @Version 1.0
 **/
@RestController
@RequestMapping("/admin/admin")
public class AdminController {
    @Autowired
    IAdminService adminService;
    @Autowired
    BaseService baseService;
    @Autowired
    MailUtil mailUtil;
    /**
     * 添加员工
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    public BaseRes addAdmin(HttpServletRequest request) {
        int result = 0;
        try{
            String jobNumber = null;
            String name =  request.getParameter("name");
            String sex =  request.getParameter("sex");
            String email =  request.getParameter("email");
            String telNum =  request.getParameter("telNum");
            String roleId =  request.getParameter("roleId");
            String shopNo =  request.getParameter("shopNo");
            Map<String, Object> reqAdminMap = new HashMap<>();
            reqAdminMap.put("role_id",roleId);
            reqAdminMap.put("admin_name",name);
            reqAdminMap.put("admin_sex",sex);
            reqAdminMap.put("admin_email",email);
            reqAdminMap.put("admin_tel_num",telNum);
            reqAdminMap.put("shop_no",shopNo);
            reqAdminMap.put("password", AESUtil.aesEncrypt(LaundryConsts.INITIAL_PASSWORD,LaundryConsts.WORKER_KEY));
            //取到员工工号
            Map<String, Object> resMap = adminService.getAdminNO(reqAdminMap);
            if(null == resMap){
                if(LaundryConsts.ROLE_ID_CENTER_ADMIN.equals(roleId )){ //干洗中心管理员
                    reqAdminMap.put("admin_no", LaundryConsts.ADMIN_NO_ADMIN);
                    jobNumber = LaundryConsts.ADMIN_NO_ADMIN;
                }else if(LaundryConsts.ROLE_ID_LAUNDRY_SHOP.equals( roleId )){ //门店管理员的工号
                    reqAdminMap.put("admin_no", LaundryConsts.ADMIN_NO_LAUNDRY_SHOP);
                    jobNumber = LaundryConsts.ADMIN_NO_LAUNDRY_SHOP;
                }else if(LaundryConsts.ROLE_ID_LAUNDRY.equals( roleId )){  //门店员工的工号
                    reqAdminMap.put("admin_no", LaundryConsts.ADMIN_NO_LAUNDRY);
                    jobNumber = LaundryConsts.ADMIN_NO_LAUNDRY;
                }else { //干洗中心员工工号
                    reqAdminMap.put("admin_no", LaundryConsts.ADMIN_NO_WORKER);
                    jobNumber = LaundryConsts.ADMIN_NO_WORKER;
                }
            }else {
                reqAdminMap.put("admin_no", resMap.get("admin_no"));
                BigDecimal bigDecimal = new BigDecimal((Double) resMap.get("admin_no"));
                jobNumber = bigDecimal.toString();
            }
            result = adminService.addAdmin(reqAdminMap);
            if( 0 < result){
                //发送入职通知邮件
                Message message = new Message();
                message.setUsername(name);
                if(LaundryConsts.MAN .equals(sex)){
                    message.setGender(LaundryConsts.GENTLEMAN);
                }else{
                    message.setGender(LaundryConsts.MADAM);
                }
                message.setInitalPassword(LaundryConsts.INITIAL_PASSWORD);
                message.setJobNumber(jobNumber);
                mailUtil.sendMailAccountMsg(message,email,LaundryConsts.ENTRY_SUBJECT,LaundryConsts.ENTRY_TEMPLATE);
            }
        }catch (DataAccessException e){
            return BaseRes.getException("数据库操作异常");
        }catch (Exception e){
            return BaseRes.getException("添加员工失败");
        }
        return BaseRes.getSuccess(result);
    }

    /**
     * 查询员工详细信息
     */
    @ResponseBody
    @RequestMapping(value = "/getAdminInfoList")
    public BaseRes getAdminInfoList(HttpServletRequest request){
        Map<String, Object> reqMap = new HashMap<>();
        Map<String, Object> resMap = new HashMap<>();
        long t1 = 0;
        long t2 = 0;
        try{
            String pageNumber =  request.getParameter("pageNumber");
            String pageSize =  request.getParameter("pageSize");
            String shop_no =  request.getParameter("shopNo");
            String roleIds = request.getParameter("roleIds");
            String name = request.getParameter("name");
            String roleName = request.getParameter("roleName");
            String shopName = request.getParameter("shopName");
            if( null != pageNumber && null != pageSize){
                reqMap.put("pageNum",pageNumber);
                reqMap.put("pageSize",pageSize);
            }
            reqMap.put("roleIds", StringHelper.stringToList(roleIds));
            reqMap.put("admin_name",name);
            reqMap.put("role_name",roleName);
            reqMap.put("shop_name",shopName);
            reqMap.put("shop_no",shop_no);
            t1 = System.currentTimeMillis();
            resMap = baseService.queryForPage("com.cqnu.web.mapper.AdminMapper.getAdminList",reqMap);
            t2 = System.currentTimeMillis();
        }catch (DataAccessException e){
            return BaseRes.getException("数据库操作异常");
        }catch (Exception e){
            return BaseRes.getException("查询员工异常", t2 - t1);
        }
        return BaseRes.getSuccess(resMap, t2 - t1);
    }
    /**
     * 删除员工信息
     */
    @ResponseBody
    @RequestMapping(value = "/delete")
    public BaseRes deleteAdminByAdminNo(HttpServletRequest request){
        int result = 0;
        try{
            String adminNo =  request.getParameter("adminNo");
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("admin_no",adminNo);
            result= adminService.deleteAdminByAdminNo(reqMap);
        }catch (DataAccessException e){
            return BaseRes.getException("数据库操作异常");
        }catch (Exception e){
            return BaseRes.getException("删除员工信息");
        }
        return BaseRes.getSuccess(result);
    }
    /**
     * 更改员工个人信息
     */
    @ResponseBody
    @RequestMapping(value = "/updateAdminPersonInfo")
    public BaseRes updateAdminInfo(HttpServletRequest request){
        int result = 0;
        try{
            String adminNo =  request.getParameter("adminNo");
            String name =  request.getParameter("name");
            String sex =  request.getParameter("sex");
            String email =  request.getParameter("email");
            String telNum =  request.getParameter("telNum");
            String password =  request.getParameter("password");
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("admin_no",adminNo);
            reqMap.put("admin_name",name);
            reqMap.put("admin_sex",sex);
            reqMap.put("admin_email",email);
            reqMap.put("admin_tel_num",telNum);
            if(password != null){
                password = AESUtil.aesEncrypt(password,LaundryConsts.WORKER_KEY);
                reqMap.put("password",password);
            }else{
                reqMap.put("password","");
            }
            result= adminService.updateAdminInfo(reqMap);
        }catch (DataAccessException e){
            return BaseRes.getException("数据库操作异常");
        }catch (Exception e){
            return BaseRes.getException("更改信息失败");
        }
        return BaseRes.getSuccess(result);
    }
    /**
     * 管理员更改员工信息
     */
    @ResponseBody
    @RequestMapping(value = "/updateAdminInfo")
    public BaseRes updateAdminRoleAndShopInfo(HttpServletRequest request){
        int result = 0;
        try{
            String adminNo =  request.getParameter("adminNo");
            String roleId =  request.getParameter("roleId");
            String shopNo =  request.getParameter("shopNo");
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("admin_no",adminNo);
            reqMap.put("role_id",roleId);
            reqMap.put("shop_no",shopNo);
            result= adminService.updateAdminRoleAndShopInfo(reqMap);
        }catch (DataAccessException e){
            return BaseRes.getException("数据库操作异常");
        }catch (Exception e){
            return BaseRes.getException("更改信息失败");
        }
        return BaseRes.getSuccess(result);
    }
    /**
     * 发送邮箱验证码
     */
    @ResponseBody
    @RequestMapping(value = "/captcha")
    public BaseRes sendEmailCaptcha(HttpServletRequest request) {
        String uuidStr = "";
        Map<String, Object> resMap = new HashMap<>();
        try{
            UUID uuid = UUID.randomUUID();
            int num = uuid.toString().hashCode();
            if (num < 0) {
                num = -num;
            }
            uuidStr = String.format("%09d", num).substring(0,6);
            String email = request.getParameter("email");
            Message message = new Message();
            message.setCaptcha(uuidStr);
            mailUtil.sendMailAccountMsg(message,email,LaundryConsts.CAPTCHA_EMAIL_SUBJECT,LaundryConsts.CAPTCHA_EMAIL);
            resMap.put("uuid",uuidStr);
            resMap.put("email",email);
        }catch (Exception e){
            return BaseRes.getException("发送邮件失败");
        }
        return BaseRes.getSuccess(resMap);
    }
}
