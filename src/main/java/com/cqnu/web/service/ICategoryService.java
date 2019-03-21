package com.cqnu.web.service;

import java.util.Map;

/**
 * @Description category表的service接口层
 * @Author xzchen
 * @Date 2019/3/19 9:49
 * @Version 1.0
 **/
public interface ICategoryService {
    /**
     * 获取最大+1的分类编号
     * @return
     */
    Map<String,Object> getCatNo();
    /**
     * 添加分类
     * @param param
     * @return
     */
    int addCategory(Map<String,Object> param);

    /**
     * 修改分类
     * @param param
     * @return
     */
    int updateCategory(Map<String,Object> param);

    /**
     * 删除分类
     * @param param
     * @return
     */
    int deleteCategory(Map<String,Object> param);
}
