package com.cqnu.web.controller;

import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.model.BaseRes;
import com.cqnu.base.model.Message;
import com.cqnu.base.util.AESUtil;
import com.cqnu.web.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 地址
 * @Author xzchen
 * @Date 2019/3/24 11:22
 * @Version 1.0
 **/
@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    IAddressService addressService;

    /**
     * 添加顾客地址
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    public BaseRes addAddress(HttpServletRequest request){
        int result = 0;
        try{
            String custId =  request.getParameter("custId");
            String address =  request.getParameter("address");
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("custId",custId);
            reqMap.put("address",address);
            result = addressService.addAddress(reqMap);
        }catch (DataAccessException e){
            return BaseRes.getException("数据库操作异常");
        }catch (Exception e){
            return BaseRes.getException("添加地址失败");
        }
        return BaseRes.getSuccess(result);
    }
    /**
     * 修改顾客地址
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updare")
    public BaseRes updareAddress(HttpServletRequest request){
        int result = 0;
        try{
            String custId =  request.getParameter("custId");
            String addressId =  request.getParameter("custId");
            String address =  request.getParameter("address");
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("custId",custId);
            reqMap.put("address",address);
            reqMap.put("addressId",addressId);
            result = addressService.updateAddress(reqMap);
        }catch (DataAccessException e){
            return BaseRes.getException("数据库操作异常");
        }catch (Exception e){
            return BaseRes.getException("修改地址失败");
        }
        return BaseRes.getSuccess(result);
    }
    /**
     * 查询顾客所有地址
     */
    @ResponseBody
    @RequestMapping(value = "/getCustAddress")
    public BaseRes getCategoryList(HttpServletRequest request){
        Map<String, Object> reqMap = new HashMap<>();
        Map<String, Object> resMap = new HashMap<>();
        long t1 = 0;
        long t2 = 0;
        try{
            String custId =  request.getParameter("custId");
            reqMap.put("custId",custId);
            t1 = System.currentTimeMillis();
            resMap = addressService.getCustAddress(reqMap);
            t2 = System.currentTimeMillis();
        }catch (DataAccessException e){
            return BaseRes.getException("数据库操作异常",t2-t1);
        }catch (Exception e){
            return BaseRes.getException("获取地址失败",t2-t1);
        }
        return BaseRes.getSuccess(resMap,t2-t1);
    }
}
