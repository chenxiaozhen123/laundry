package com.cqnu.web.controller;

import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.common.exception.LaundryException;
import com.cqnu.base.service.BaseService;
import com.cqnu.web.service.ICategoryService;
import com.cqnu.web.service.IGoodsService;
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
    public int addCategory(HttpServletRequest request){
        int result = 0;
        try{
            String catName =  request.getParameter("catName");
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("cat_name",catName);
            //获取最大+1的分类编号
            Map<String, Object> catNOMap = categoryService.getCatNo();
            if(null != catNOMap){
                BigDecimal bigDecimal = new BigDecimal((Double) catNOMap.get("cat_no"));
                reqMap.put("cat_no",bigDecimal.toString());
            }else {
                reqMap.put("cat_no", LaundryConsts.CAT_NO);
            }
            result = categoryService.addCategory(reqMap);
        }catch (DataAccessException e){
            throw new LaundryException("数据库操作异常");
        }catch (Exception ex){
            throw new LaundryException("新增分类失败");
        }
        return result;
    }
    /**
     * 删除分类
     */
    @ResponseBody
    @RequestMapping(value = "/delete")
    @Transactional
    public int deleteCategory(HttpServletRequest request){
        int result = 0;
        try{
            String catNo =  request.getParameter("catNo");
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("catNo",catNo);
            result = categoryService.deleteCategory(reqMap);
            if(0 < result){
                result = goodsService.deleteGoodsList(reqMap);
            }
        }catch (DataAccessException e){
            throw new LaundryException("数据库操作异常");
        }catch (Exception e){
            throw new LaundryException("删除分类异常");
        }
        return result;
    }
    /**
     * 修改分类
     */
    @ResponseBody
    @RequestMapping(value = "/update")
    public int updateCategory(HttpServletRequest request){
        int result = 0;
        try{
            String catNo =  request.getParameter("catNo");
            String catName =  request.getParameter("catName");
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("catNo",catNo);
            reqMap.put("catName",catName);
            result = categoryService.updateCategory(reqMap);
        }catch (DataAccessException e){
            throw new LaundryException("数据库操作异常");
        }catch (Exception e){
            throw new LaundryException("修改分类异常");
        }
        return result;
    }
    /**
     * 查询所有分类
     */
    @ResponseBody
    @RequestMapping(value = "/getCategoryList")
    public Map<String, Object> getCategoryList(HttpServletRequest request){
        Map<String, Object> reqMap = new HashMap<>();
        Map<String, Object> resMap = new HashMap<>();
        try{
            String pageNumber =  request.getParameter("pageNumber");
            String pageSize =  request.getParameter("pageSize");
            String catName = request.getParameter("catName");
            reqMap.put("pageNum",pageNumber);
            reqMap.put("pageSize",pageSize);
            reqMap.put("catName",catName);
            resMap = baseService.queryForPage("com.cqnu.web.mapper.CategoryMapper.getCategoryList",reqMap);
        }catch (DataAccessException e){
            throw new LaundryException("数据库操作异常");
        }catch (Exception e){
            throw new LaundryException("查询分类异常");
        }

        return resMap;
    }

}
