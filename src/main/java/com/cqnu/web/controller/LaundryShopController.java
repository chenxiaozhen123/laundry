package com.cqnu.web.controller;

import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.common.exception.LaundryException;
import com.cqnu.base.model.BaseRes;
import com.cqnu.base.service.BaseService;
import com.cqnu.web.service.IAdminService;
import com.cqnu.web.service.ILaundryShopService;
import com.cqnu.web.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 门店管理
 * @Author xzchen
 * @Date 2019/3/14 17:48
 * @Version 1.0
 **/
@RestController
@RequestMapping("/admin/shop")
public class LaundryShopController {
    private static Logger logger = LoggerFactory.getLogger(LaundryShopController.class);
    private static String calssPath = "com.cqnu.web.controller.LaundryShopController";
    private final int roleId = 2;
    private String adminNo ;
    @Autowired
    IAdminService adminService;
    @Autowired
    ILaundryShopService laundryShopService;
    @Autowired
    BaseService baseService;
    /**
     * 添加门店
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    public BaseRes addLaundryShop(HttpServletRequest request) {
        int result = 0;
        try{
            String shopName =  request.getParameter("shopName");
            String principalNo = request.getParameter("principalNo");
            String address = request.getParameter("address");
            String area =  request.getParameter("area");
            Map<String, Object> reqMap = new HashMap<>();
            //获取最大的门店编号
            Map<String, Object> shopNOMap = laundryShopService.getShopNO();
            Map<String, Object> reqAdminMap = new HashMap<>();
            reqMap.put("shop_address",address);
            reqMap.put("shop_area",area);
            reqMap.put("shop_name",shopName);
            reqMap.put("principal_no", principalNo);
            String maxShopNO = shopNOMap.get("shop_no").toString();
            if(null == shopNOMap || LaundryConsts.LAUNDRY_NO_INID .equals(maxShopNO)){
                reqMap.put("shop_no",LaundryConsts.SHOP_NO);
                reqAdminMap.put("shop_no",LaundryConsts.SHOP_NO);
            }else {
                reqMap.put("shop_no",shopNOMap.get("shop_no"));
                reqAdminMap.put("shop_no",shopNOMap.get("shop_no"));
            }
            reqAdminMap.put("admin_no",principalNo);
            Map<String, Object> resMap = adminService.getShopCategory(reqAdminMap);
            if(null != resMap){
                String shopCategory = resMap.get("shop_category").toString();
                reqMap.put("shop_category",shopCategory);
            }
            result= laundryShopService.addLaundryShop(reqMap);
            if( 0 < result){ //门店负责人添加成功则去admin表更改该员工的所属门店
                result = 0;
                result = adminService.updateShopIdByAdminNo(reqAdminMap);
                if( 0 < result){
                    return BaseRes.getSuccess();
                }
                else{
                    logger.error(calssPath+"：添加门店失败");
                    return BaseRes.getFailure("添加门店失败");
                }
            }else{
                logger.error(calssPath+"：添加门店失败");
                return BaseRes.getFailure("添加门店失败");
            }
        }catch (DataAccessException e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("数据库异常");
        }catch (Exception e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("添加门店失败");
        }
    }
    /**
     * 查询所有门店
     */
    @ResponseBody
    @RequestMapping(value = "/getLaundryShopList")
    public BaseRes getLaundryShopList(HttpServletRequest request){
        Map<String, Object> reqMap = new HashMap<>();
        Map<String, Object> resMap = new HashMap<>();
        long t1 = 0;
        long t2 = 0;
        try{
            String pageNumber =  request.getParameter("pageNumber");
            String pageSize =  request.getParameter("pageSize");
            String name =  request.getParameter("name");
            String area =  request.getParameter("area");
            String roleIds =  request.getParameter("roleIds");
            reqMap.put("roleIds", StringHelper.stringToList(roleIds));
            if( null != pageNumber && null != pageSize){
                reqMap.put("pageNum",pageNumber);
                reqMap.put("pageSize",pageSize);
            }
            reqMap.put("name",name);
            reqMap.put("area",area);
            t1 = System.currentTimeMillis();
            resMap = baseService.queryForPage("com.cqnu.web.mapper.LaundryShopMapper.getLaundryShopList",reqMap);
            t2 = System.currentTimeMillis();
        }catch (DataAccessException e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("数据库异常", t2 - t1);
        }catch (Exception e){
            return BaseRes.getException("查询门店信息失败", t2 - t1);
        }
        return BaseRes.getSuccess(resMap, t2 - t1);
    }
    /**
     * 顾客获取门店地址
     */
    @ResponseBody
    @RequestMapping(value = "/getCustLaundryShopList")
    public BaseRes getLaundryShopList1(HttpServletRequest request){
        Map<String, Object> reqMap = new HashMap<>();
        Map<String, Object> resMap = new HashMap<>();
        long t1 = 0;
        long t2 = 0;
        try{
            String shop_address =  request.getParameter("shop_address");
            reqMap.put("shop_address",shop_address);
            t1 = System.currentTimeMillis();
            resMap = baseService.queryForPage("com.cqnu.web.mapper.LaundryShopMapper.getCustLaundryShopList",reqMap);
            t2 = System.currentTimeMillis();
        }catch (DataAccessException e){
            return BaseRes.getException("数据库操作异常", t2 - t1);
        }catch (Exception e){
            return BaseRes.getException("查询门店信息失败", t2 - t1);
        }
        return BaseRes.getSuccess(resMap, t2 - t1);
    }
    /**
     * 删除门店
     */
    @ResponseBody
    @RequestMapping(value = "/delete")
    public BaseRes deleteLaundryShop(HttpServletRequest request){
        int result = 0;
        try{
            String shopNo =  request.getParameter("shopNo");
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("shopNo",shopNo);
            result= laundryShopService.deleteLaundryShop(reqMap);
            if( 0 < result){
                return BaseRes.getSuccess();
            }
            else{
                logger.error(calssPath+"：删除门店失败");
                return BaseRes.getFailure("删除门店失败");
            }
        }catch (DataAccessException e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("数据库异常");
        }catch (Exception e){
            logger.error(calssPath+"：删除门店异常",e.getMessage());
            return BaseRes.getException("删除门店异常");
        }
    }
    /**
     * 修改门店信息
     */
    @ResponseBody
    @RequestMapping(value = "/update")
    public BaseRes updateLaundryShop(HttpServletRequest request){
        int result = 0;
        try{
            String shopNo =  request.getParameter("shopNo");
            String shopName =  request.getParameter("shopName");
            String principalNo = request.getParameter("principalNo");
            String address = request.getParameter("address");
            String area =  request.getParameter("area");
            Map<String, Object> reqMap = new HashMap<>();
            Map<String, Object> reqAdminMap = new HashMap<>();
            reqMap.put("shop_address",address);
            reqMap.put("shop_area",area);
            reqMap.put("shop_name",shopName);
            reqMap.put("principal_no", principalNo);
            reqMap.put("shop_no",shopNo);
            reqAdminMap.put("admin_no",principalNo);
            reqAdminMap.put("shop_no",shopNo);
            result= laundryShopService.updateLaundryShop(reqMap);
            if( 0 < result){ //门店负责人信息修改成功则去admin表更改该员工的所属门店
                result = 0;
                result = adminService.updateShopIdByAdminNo(reqAdminMap);
                if( 0 < result){
                    return BaseRes.getSuccess();
                }
                else{
                    logger.error(calssPath+"：修改门店信息失败");
                    return BaseRes.getFailure("修改门店信息失败");
                }
            }else{
                logger.error(calssPath+"：修改门店信息失败");
                return BaseRes.getFailure("修改门店信息失败");
            }
        }catch (DataAccessException e){
            return BaseRes.getException("数据库操作异常");
        }catch (Exception e){
            return BaseRes.getException("修改门店信息失败");
        }
    }
}
