package com.cqnu.web.service.impl;

import com.cqnu.base.service.impl.BaseServiceImpl;
import com.cqnu.web.service.IOrderService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description 订单
 * @Author xzchen
 * @Date 2019/3/17 14:57
 * @Version 1.0
 **/
@Service("orderService")
public class OrderServiceImpl extends BaseServiceImpl implements IOrderService{
    private static final String MAPPER_URL = "com.cqnu.web.mapper.OrderMapper.";
    @Override
    public int addOrder(Map<String, Object> params) {
        return session.insert(MAPPER_URL+"",params);
    }

    @Override
    public Map<String, Object> getOrderList(Map<String, Object> params) {
        return session.selectOne(MAPPER_URL+"",params);
    }

    @Override
    public int handleOrder(Map<String, Object> params) {
        return session.update(MAPPER_URL+"handleOrder",params);
    }
}
