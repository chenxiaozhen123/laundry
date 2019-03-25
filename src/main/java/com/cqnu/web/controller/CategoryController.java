package com.cqnu.web.controller;

import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.common.exception.LaundryException;
import com.cqnu.base.model.BaseRes;
import com.cqnu.base.service.BaseService;
import com.cqnu.web.service.ICategoryService;
import com.cqnu.web.service.IGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 分类管理控制层
 * @Author xzchen
 * @Date 2019/3/19 10:04
 * @Version 1.0
 **/
@RestController
@RequestMapping("/admin/cat")
public class CategoryController {
    private static Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private static String calssPath = "com.cqnu.web.controller.CategoryController";
    @Autowired
    ICategoryService categoryService;
    @Autowired
    BaseService baseService;
    @Autowired
    IGoodsService goodsService;
    /**
     * 添加分类
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    public BaseRes addCategory(HttpServletRequest request){
        int result = 0;
        try{
            String catName =  request.getParameter("catName");
            String imgPath =  request.getParameter("imgPath");
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("cat_name",catName);
            reqMap.put("img_path",imgPath);

            //获取最大+1的分类编号
            Map<String, Object> catNOMap = categoryService.getCatNo();
            if(null != catNOMap){
                BigDecimal bigDecimal = new BigDecimal((Double) catNOMap.get("cat_no"));
                reqMap.put("cat_no",bigDecimal.toString());
            }else {
                reqMap.put("cat_no", LaundryConsts.CAT_NO);
            }
            result = categoryService.addCategory(reqMap);
            if( 0 < result){
                return BaseRes.getSuccess();
            }
            else{
                logger.error(calssPath+"：评价订单失败");
                return BaseRes.getFailure("新增分类失败");
            }
        }catch (DataAccessException e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("数据库异常");
        }catch (Exception ex){
            logger.error(calssPath+"：数据库异常",ex.getMessage());
            return BaseRes.getException("新增分类失败");
        }
    }
    /**
     * 删除分类
     */
    @ResponseBody
    @RequestMapping(value = "/delete")
    @Transactional
    public BaseRes deleteCategory(HttpServletRequest request){
        int result = 0;
        try{
            String catNo =  request.getParameter("catNo");
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("catNo",catNo);
            result = categoryService.deleteCategory(reqMap);
            if(0 < result){
                result = 0;
                result = goodsService.deleteGoodsList(reqMap);
                if( 0 < result){
                    return BaseRes.getSuccess();
                }
                else{
                    logger.error(calssPath+"：删除分类异常失败");
                    return BaseRes.getFailure("删除分类异常失败");
                }
            }else{
                logger.error(calssPath+"：删除分类异常");
                return BaseRes.getFailure("删除分类异常");
            }
        }catch (DataAccessException e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("数据库异常");
        }catch (Exception e){
            logger.error(calssPath+"：删除分类异常",e.getMessage());
            return BaseRes.getException("删除分类异常");
        }
    }
    /**
     * 修改分类
     */
    @ResponseBody
    @RequestMapping(value = "/update")
    public BaseRes updateCategory(HttpServletRequest request){
        int result = 0;
        try{
            String catNo =  request.getParameter("catNo");
            String catName =  request.getParameter("catName");
            String imgPath =  request.getParameter("imgPath");
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("catNo",catNo);
            reqMap.put("catName",catName);
            reqMap.put("imgPath",imgPath);
            result = categoryService.updateCategory(reqMap);
            if( 0 < result){
                return BaseRes.getSuccess();
            }
            else{
                logger.error(calssPath+"：评价订单失败");
                return BaseRes.getFailure("修改分类失败");
            }
        }catch (DataAccessException e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("数据库异常");
        }catch (Exception e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("修改分类异常");
        }
    }
    /**
     * 查询所有分类
     */
    @ResponseBody
    @RequestMapping(value = "/getCategoryList")
    public BaseRes getCategoryList(HttpServletRequest request){
        Map<String, Object> reqMap = new HashMap<>();
        Map<String, Object> resMap = new HashMap<>();
        long t1 = 0;
        long t2 = 0;
        try{
            String pageNumber =  request.getParameter("pageNumber");
            String pageSize =  request.getParameter("pageSize");
            String catName = request.getParameter("catName");
            if( null != pageNumber && null != pageSize){
                reqMap.put("pageNum",pageNumber);
                reqMap.put("pageSize",pageSize);
            }
            reqMap.put("catName",catName);
            resMap = baseService.queryForPage("com.cqnu.web.mapper.CategoryMapper.getCategoryList",reqMap);
        }catch (DataAccessException e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("数据库异常",t2-t1);
        }catch (Exception e){
            logger.error(calssPath+"：查询分类异常",e.getMessage());
            return BaseRes.getException("查询分类异常",t2-t1);
        }
        return BaseRes.getSuccess(resMap,t2-t1);
    }

}
