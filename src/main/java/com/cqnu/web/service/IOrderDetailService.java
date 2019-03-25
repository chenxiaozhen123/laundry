package com.cqnu.web.service;

import com.cqnu.web.entity.OrderDetail;

import java.util.List;

/**
 * @Description TODO
 * @Author xzchen
 * @Date 2019/3/26 0:45
 * @Version 1.0
 **/
public interface IOrderDetailService {
    /**
     * 批量插入订单详情数据
     * @param orderDetails
     * @return
     */
    int insertOrderDetailBatch(List<OrderDetail> orderDetails);
}
