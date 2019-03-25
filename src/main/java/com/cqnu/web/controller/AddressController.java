package com.cqnu.web.controller;

import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.model.BaseRes;
import com.cqnu.base.model.Message;
import com.cqnu.base.service.BaseService;
import com.cqnu.base.util.AESUtil;
import com.cqnu.web.service.IAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger logger = LoggerFactory.getLogger(AddressController.class);
    private static String calssPath = "com.cqnu.web.controller.AddressController";
    @Autowired
    IAddressService addressService;
    @Autowired
    BaseService baseService;

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
            String mobile =  request.getParameter("mobile");
            String recevier =  request.getParameter("recevier");
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("custId",custId);
            reqMap.put("address",address);
            reqMap.put("mobile",mobile);
            reqMap.put("recevier",recevier);
            result = addressService.addAddress(reqMap);
            if( 0 < result){
                return BaseRes.getSuccess();
            }
            else{
                logger.error(calssPath+"：添加地址失败");
                return BaseRes.getFailure("添加地址失败");
            }
        }catch (DataAccessException e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("数据库异常");
        }catch (Exception e){
            logger.error(calssPath+"：添加地址失败",e.getMessage());
            return BaseRes.getException("添加地址失败");
        }
    }
    /**
     * 修改顾客地址
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update")
    public BaseRes updareAddress(HttpServletRequest request){
        int result = 0;
        try{
            String custId =  request.getParameter("custId");
            String addressId =  request.getParameter("addressId");
            String address =  request.getParameter("address");
            String mobile =  request.getParameter("mobile");
            String recevier =  request.getParameter("recevier");
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("custId",custId);
            reqMap.put("address",address);
            reqMap.put("mobile",mobile);
            reqMap.put("addressId",addressId);
            reqMap.put("recevier",recevier);
            result = addressService.updateAddress(reqMap);
            if( 0 <result){
                return BaseRes.getSuccess(result);
            }else{
                logger.error(calssPath+"：添加地址失败");
                return BaseRes.getFailure("修改失败");
            }
        }catch (DataAccessException e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("数据库异常");
        }catch (Exception e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("修改地址失败");
        }

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
            resMap = baseService.queryForPage("com.cqnu.web.mapper.AddressMapper.getCustAddress",reqMap);
            t2 = System.currentTimeMillis();
        }catch (DataAccessException e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("数据库异常",t2-t1);
        }catch (Exception e){
            logger.error(calssPath+"：获取地址失败",e.getMessage());
            return BaseRes.getException("获取地址失败",t2-t1);
        }
        return BaseRes.getSuccess(resMap,t2-t1);
    }
}
