package com.cqnu.web.service;

import java.util.List;
import java.util.Map;

/**
 * @Description 订单业务层
 * @Author xzchen
 * @Date 2019/3/17 14:56
 * @Version 1.0
 **/
public interface IOrderService {
    /**
     * 创建订单
     * @param params
     * @return
     */
    int addOrder(Map<String, Object> params);

    /**
     * 查询订单
     * @param params
     * @return
     */
    Map<String, Object> getOrderList(Map<String, Object> params);

    /**
     * 处理订单
     * @param params
     * @return
     */
    int handleOrder(Map<String, Object> params);

    /**
     * 取消订单
     * @param param
     * @return
     */
    int cancelOrder(Map<String,Object> param);
    /**
     * 查询顾客最新的一条订单信息
     * @param params
     * @return
     */
    Map<String, Object> getOrderLastByCust(Map<String, Object> params);

    /**
     * 根据订单号获取门店负责人的邮箱
     * @param params
     * @return
     */
    Map<String,Object> getPrincipalEmailByOrderId(Map<String,Object> params);

}
