package com.cqnu.web.service;

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
}