package com.cqnu.web.controller;

import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.common.exception.LaundryException;
import com.cqnu.base.model.BaseRes;
import com.cqnu.base.service.BaseService;
import com.cqnu.web.service.IGoodsService;
import com.cqnu.web.util.StringHelper;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description 商品控制器
 * @Author xzchen
 * @Date 2019/3/19 16:47
 * @Version 1.0
 **/
@RestController
@RequestMapping("/admin/goods")
public class GoodsController {
    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);
    private static String calssPath = "com.cqnu.web.controller.GoodsController";
    @Autowired
    IGoodsService goodsService;
    @Autowired
    BaseService baseService;
    /**
     * 添加商品
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    public BaseRes addGoods(HttpServletRequest request){
        int result = 0;
        Map<String, Object> reqMap = new HashMap<>();
        Map<String, Object> resMap = new HashMap<>();
        try{
            String goodsNO = "";
            String goodsName = request.getParameter("goodsName");
            String catNo = request.getParameter("catNo");
            String price = request.getParameter("price");
            String imgPath = request.getParameter("imgPath");
            resMap = goodsService.getMaxGoodsNo();
            if( null != resMap){
                BigDecimal bigDecimal = new BigDecimal((Double) resMap.get("goods_no"));
                goodsNO = bigDecimal.toString();
            }else{
                goodsNO = LaundryConsts.GOODS_NO;
            }
            reqMap.put("goodsName",goodsName);
            reqMap.put("catNo",catNo);
            reqMap.put("price",price);
            reqMap.put("imgPath",imgPath);
            reqMap.put("goodsNO",goodsNO);
            result = goodsService.addGoods(reqMap);
            if( 0 < result){
                return BaseRes.getSuccess();
            }
            else{
                logger.error(calssPath+"：添加商品失败");
                return BaseRes.getFailure("添加商品失败");
            }
        }catch (DataAccessException e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("数据库异常");
        }catch (Exception ex){
            logger.error(calssPath+"：数据库异常",ex.getMessage());
            return BaseRes.getException("添加商品失败");
        }

    }
    /**
     * 删除商品
     */
    @ResponseBody
    @RequestMapping(value = "/delete")
    public BaseRes deleteGoods(HttpServletRequest request){
        int result = 0;
        Map<String, Object> reqMap = new HashMap<>();
        try{
            String goodsNO = request.getParameter("goodsNO");
            reqMap.put("goodsNO",goodsNO);
            result = goodsService.deleteGoods(reqMap);
            if( 0 < result){
                return BaseRes.getSuccess();
            }
            else{
                logger.error(calssPath+"：删除商品失败");
                return BaseRes.getFailure("删除商品失败");
            }
        }catch (DataAccessException e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("数据库异常");
        }catch (Exception ex){
            logger.error(calssPath+"：数据库异常",ex.getMessage());
            return BaseRes.getException("删除商品失败");
        }
    }
    /**
     * 修改商品
     */
    @ResponseBody
    @RequestMapping(value = "/update")
    public BaseRes updateGoods(HttpServletRequest request){
        int result = 0;
        Map<String, Object> reqMap = new HashMap<>();
        Map<String, Object> resMap = new HashMap<>();
        try{
            String goodsName = request.getParameter("goodsName");
            String goodsNO = request.getParameter("goodsNO");
            String price = request.getParameter("price");
            String imgPath = request.getParameter("imgPath");
            reqMap.put("goodsName",goodsName);
            reqMap.put("price",price);
            reqMap.put("imgPath",imgPath);
            reqMap.put("goodsNO",goodsNO);
            result = goodsService.updateGoods(reqMap);
            if( 0 < result){
                return BaseRes.getSuccess();
            }
            else{
                logger.error(calssPath+"：修改商品失败");
                return BaseRes.getFailure("修改商品失败");
            }
        }catch (DataAccessException e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("数据库异常");
        }catch (Exception ex){
            logger.error(calssPath+"：数据库异常",ex.getMessage());
            return BaseRes.getException("修改商品失败");
        }
    }
    /**
     * 查询分类下的所有商品
     */
    @ResponseBody
    @RequestMapping(value = "/getGoodsList" ,method ={RequestMethod.POST,RequestMethod.GET})
    public BaseRes getAdminInfoList(HttpServletRequest request){
        Map<String, Object> reqMap = new HashMap<>();
        Map<String, Object> resMap = new HashMap<>();
        long t1 = 0;
        long t2 = 0;
        try{
            String pageNumber =  request.getParameter("pageNumber");
            String pageSize =  request.getParameter("pageSize");
            String catNO = request.getParameter("catNo");
            String goodsName =  request.getParameter("goodsName");
            if( null != pageNumber && null != pageSize){
                reqMap.put("pageNum",pageNumber);
                reqMap.put("pageSize",pageSize);
            }
            reqMap.put("catNO", catNO);
            reqMap.put("goodsName",goodsName);
            t1 = System.currentTimeMillis();
            resMap = baseService.queryForPage("com.cqnu.web.mapper.GoodsMapper.getGoodsList",reqMap);
            t2 = System.currentTimeMillis();
        }catch (DataAccessException e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("数据库异常");
        }catch (Exception ex){
            logger.error(calssPath+"：数据库异常",ex.getMessage());
            return BaseRes.getException("查询商品失败");
        }
        return BaseRes.getSuccess(resMap,t2-t1);
    }
}
