package com.cqnu.web.controller;

import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.util.AESUtil;
import com.cqnu.web.entity.Customer;
import com.cqnu.web.service.ICustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    ICustService custService;

    /**
     * 登陆
     */
    @ResponseBody
    @RequestMapping(value = "/login")
    public boolean login(HttpServletRequest request) {
        boolean flag = false;
        try{
            String mobile =  request.getParameter("mobile");
            String password =  request.getParameter("password");
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("mobile",mobile);
            reqMap.put("password", AESUtil.aesEncrypt(password, LaundryConsts.CUSTOMER));
            Map<String, Object> resMap = custService.custLogin(reqMap);
            if(null != resMap){
                flag = true;
                request.getSession().setAttribute(LaundryConsts.SESSION_USER_KEY,this.custLogin(resMap));
            }
        }catch (Exception e){

        }

        return flag;
    }

    /**
     * 封装cust参数
     * @param map
     * @return
     */
    private Customer custLogin(Map<String, Object> map){
        Customer customer = new Customer();
        customer.setCustId(Integer.valueOf(map.get("cust_id").toString()));
        customer.setCname(map.get("cname").toString());
        customer.setPassword(map.get("password").toString());
        customer.setSex(map.get("sex").toString());
        customer.setMobile(map.get("mobile").toString());
        customer.setState(map.get("state").toString());
        customer.setEffDate(Date.valueOf(map.get("eff_date").toString()));
        return customer;
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
}
