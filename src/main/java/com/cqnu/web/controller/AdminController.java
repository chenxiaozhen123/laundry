package com.cqnu.web.controller;

import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.service.BaseService;
import com.cqnu.web.service.IAdminService;
import com.cqnu.web.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    /**
     * 添加员工
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    public int addAdmin(HttpServletRequest request) {
        String name =  request.getParameter("name");
        String sex =  request.getParameter("sex");
        String email =  request.getParameter("email");
        String telNum =  request.getParameter("telNum");
        String roleId =  request.getParameter("telNum");
        Map<String, Object> reqAdminMap = new HashMap<>();
        reqAdminMap.put("role_id","role_id");
        reqAdminMap.put("admin_name",name);
        reqAdminMap.put("admin_sex",sex);
        reqAdminMap.put("admin_email",email);
        reqAdminMap.put("admin_tel_num",telNum);
        reqAdminMap.put("shop_id","-1");
        Map<String, Object> resMap = adminService.getAdminNO(reqAdminMap);
        if(null == resMap){
            if(LaundryConsts.ROLE_ID_LAUNDRY_SHOP.equals( roleId)){
                reqAdminMap.put("admin_no", LaundryConsts.ADMIN_NO_LAUNDRY_SHOP);
            }else if(LaundryConsts.ROLE_ID_LAUNDRY.equals( roleId)){
                reqAdminMap.put("admin_no", LaundryConsts.ADMIN_NO_LAUNDRY);
            }else {
                reqAdminMap.put("admin_no", LaundryConsts.ADMIN_NO_WORKER);
            }
        }else {
            reqAdminMap.put("admin_no", resMap.get("admin_no"));
        }
        int result1 = adminService.addAdmin(reqAdminMap);

        return result1;
    }
    /**
     * 查询员工
     */
    @ResponseBody
    @RequestMapping(value = "/getAdminList")
    public Map<String, Object> getAdminList(HttpServletRequest request){
        Map<String, Object> reqMap = new HashMap<>();
        Map<String, Object> resMap = new HashMap<>();
        String pageNumber =  request.getParameter("pageNumber");
        String pageSize =  request.getParameter("pageSize");
        String shop_no =  request.getParameter("shopNo");
        String roleIds = request.getParameter("roleIds");
        String name = request.getParameter("name");
        reqMap.put("pageNum",pageNumber);
        reqMap.put("pageSize",pageSize);
        reqMap.put("shop_no",shop_no);
        reqMap.put("roleIds", StringHelper.stringToList(roleIds));
        reqMap.put("admin_name",name);
        resMap = baseService.queryForPage("com.cqnu.web.mapper.AdminMapper.getAdmin",reqMap);
        return resMap;
    }
    /**
     * 查询员工
     */
    @ResponseBody
    @RequestMapping(value = "/getAdminInfoList")
    public Map<String, Object> getAdminInfoList(HttpServletRequest request){
        Map<String, Object> reqMap = new HashMap<>();
        Map<String, Object> resMap = new HashMap<>();
        String pageNumber =  request.getParameter("pageNumber");
        String pageSize =  request.getParameter("pageSize");
        String shop_no =  request.getParameter("shopNo");
        String roleIds = request.getParameter("roleIds");
        String name = request.getParameter("name");
        String shopName = request.getParameter("shopName");
        reqMap.put("pageNum",pageNumber);
        reqMap.put("pageSize",pageSize);
        reqMap.put("shop_no",shop_no);
        reqMap.put("roleIds", StringHelper.stringToList(roleIds));
        reqMap.put("admin_name",name);
        reqMap.put("shop_name",shopName);
        resMap = baseService.queryForPage("com.cqnu.web.mapper.AdminMapper.getAdminList",reqMap);
        return resMap;
    }
}
