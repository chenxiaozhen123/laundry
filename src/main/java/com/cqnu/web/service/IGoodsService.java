package com.cqnu.web.service;

import java.util.Map;

/**
 * @Description goods表service接口
 * @Author xzchen
 * @Date 2019/3/20 4:32
 * @Version 1.0
 **/
public interface IGoodsService {
    /**
     * 增加商品
     * @param param
     * @return
     */
    int addGoods(Map<String,Object> param);

    /**
     * 删除商品
     * @param param
     * @return
     */
    int deleteGoods(Map<String,Object> param);

    /**
     * 修改商品
     * @param param
     * @return
     */
    int updateGoods(Map<String,Object> param);

    /**
     * 获取最大商品编号+1
     * @return
     */
    Map<String,Object> getMaxGoodsNo();

    /**
     * 删除整个分类下的所有商品
     * @param param
     * @return
     */
    int deleteGoodsList(Map<String,Object> param);
}
