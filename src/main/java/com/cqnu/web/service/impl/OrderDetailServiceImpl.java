package com.cqnu.web.service.impl;

import com.cqnu.base.service.impl.BaseServiceImpl;
import com.cqnu.web.entity.OrderDetail;
import com.cqnu.web.service.IOrderDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 订单详情
 * @Author xzchen
 * @Date 2019/3/26 0:46
 * @Version 1.0
 **/
@Service("orderDetailService")
public class OrderDetailServiceImpl extends BaseServiceImpl implements IOrderDetailService {
    private static final String MAPPER_URL = "com.cqnu.web.mapper.OrderDetailMapper.";
    @Override
    public int insertOrderDetailBatch(List<OrderDetail> orderDetails) {
        return session.insert(MAPPER_URL+"insertOrderDetailBatch",orderDetails);
    }
}
