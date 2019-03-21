package com.cqnu.web.controller;

import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.common.exception.LaundryException;
import com.cqnu.base.service.BaseService;
import com.cqnu.web.service.IGoodsService;
import com.cqnu.web.util.StringHelper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @Autowired
    IGoodsService goodsService;
    @Autowired
    BaseService baseService;
    /**
     * 添加商品
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    public int addGoods(HttpServletRequest request){
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
        }catch (DataAccessException e){
            throw new LaundryException("数据库操作异常");
        }catch (Exception ex){
            throw new LaundryException("添加商品失败");
        }

        return result;
    }
    /**
     * 删除商品
     */
    @ResponseBody
    @RequestMapping(value = "/delete")
    public int deleteGoods(HttpServletRequest request){
        int result = 0;
        Map<String, Object> reqMap = new HashMap<>();
        try{
            String goodsNO = request.getParameter("goodsNO");
            reqMap.put("goodsNO",goodsNO);
            result = goodsService.deleteGoods(reqMap);
        }catch (DataAccessException e){
            throw new LaundryException("数据库操作异常");
        }catch (Exception ex){
            throw new LaundryException("删除商品失败");
        }
        return result;
    }
    /**
     * 修改商品
     */
    @ResponseBody
    @RequestMapping(value = "/update")
    public int updateGoods(HttpServletRequest request){
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
        }catch (DataAccessException e){
            throw new LaundryException("数据库操作异常");
        }catch (Exception ex){
            throw new LaundryException("修改商品失败");
        }
        return result;
    }
    /**
     * 查询分类下的所有商品
     */
    @ResponseBody
    @RequestMapping(value = "/getGoodsList")
    public Map<String, Object> getAdminInfoList(HttpServletRequest request){
        Map<String, Object> reqMap = new HashMap<>();
        Map<String, Object> resMap = new HashMap<>();
        try{
            String pageNumber =  request.getParameter("pageNumber");
            String pageSize =  request.getParameter("pageSize");
            String catNO = request.getParameter("catNo");
            String goodsName =  request.getParameter("goodsName");
            reqMap.put("pageNum",pageNumber);
            reqMap.put("pageSize",pageSize);
            reqMap.put("catNO", catNO);
            reqMap.put("goodsName",goodsName);
            resMap = baseService.queryForPage("com.cqnu.web.mapper.GoodsMapper.getGoodsList",reqMap);
        }catch (DataAccessException e){
            throw new LaundryException("数据库操作异常");
        }catch (Exception ex){
            throw new LaundryException("查询商品失败");
        }
        return resMap;
    }
}
