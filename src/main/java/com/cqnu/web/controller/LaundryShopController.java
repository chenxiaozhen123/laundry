package com.cqnu.web.controller;

import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.common.exception.LaundryException;
import com.cqnu.base.service.BaseService;
import com.cqnu.web.service.IAdminService;
import com.cqnu.web.service.ILaundryShopService;
import com.cqnu.web.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public int addLaundryShop(HttpServletRequest request) {
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
                result = adminService.updateShopIdByAdminNo(reqAdminMap);
            }
        }catch (Exception e){
            throw new LaundryException(e.getMessage());
        }
        return result;
    }
    /**
     * 查询所有门店
     */
    @ResponseBody
    @RequestMapping(value = "/getLaundryShopList")
    public Map<String, Object> getLaundryShopList(HttpServletRequest request){
        Map<String, Object> reqMap = new HashMap<>();
        Map<String, Object> resMap = new HashMap<>();
        try{
            String pageNumber =  request.getParameter("pageNumber");
            String pageSize =  request.getParameter("pageSize");
            String name =  request.getParameter("name");
            String area =  request.getParameter("area");
            String roleIds =  request.getParameter("roleIds");
            reqMap.put("roleIds", StringHelper.stringToList(roleIds));
            reqMap.put("pageNum",pageNumber);
            reqMap.put("pageSize",pageSize);
            reqMap.put("name",name);
            reqMap.put("area",area);
            resMap = baseService.queryForPage("com.cqnu.web.mapper.LaundryShopMapper.getLaundryShopList",reqMap);
        }catch (Exception e){
            throw new LaundryException(e.getMessage());
        }

        return resMap;
    }
    /**
     * 删除门店
     */
    @ResponseBody
    @RequestMapping(value = "/delete")
    public int deleteLaundryShop(HttpServletRequest request){
        int result = 0;
        try{
            String shopNo =  request.getParameter("shopNo");
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("shopNo",shopNo);
            result= laundryShopService.deleteLaundryShop(reqMap);
        }catch (Exception e){
            throw new LaundryException(e.getMessage());
        }
        return result;
    }
    /**
     * 修改门店信息
     */
    @ResponseBody
    @RequestMapping(value = "/update")
    public int updateLaundryShop(HttpServletRequest request){
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
                result = adminService.updateShopIdByAdminNo(reqAdminMap);
            }
        }catch (Exception e){
            throw new LaundryException(e.getMessage());
        }
        return result;
    }
}
