package com.cqnu.web.service.impl;

import com.cqnu.base.service.impl.BaseServiceImpl;
import com.cqnu.web.service.ILaundryShopService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description 门店
 * @Author xzchen
 * @Date 2019/3/14 17:24
 * @Version 1.0
 **/
@Service("laundryShopService")
public class LaundryShopServiceImpl extends BaseServiceImpl implements ILaundryShopService{
    private static final String MAPPER_URL = "com.cqnu.web.mapper.LaundryShopMapper.";
    @Override
    public int addLaundryShop(Map<String, Object> params) {
        return session.insert(MAPPER_URL+"addLaundryShop",params);
    }

    @Override
    public Map<String, Object> getShopNO() {
        return session.selectOne(MAPPER_URL+"getShopNo");
    }

    @Override
    public int deleteLaundryShop(Map<String, Object> params) {
        return session.update(MAPPER_URL+"deleteLaundryShop",params);
    }

    @Override
    public int updateLaundryShop(Map<String, Object> params) {
        return session.update(MAPPER_URL+"updateLaundryShop",params);
    }

    @Override
    public Map<String, Object> getShopByShopNo(Map<String, Object> params) {
        return session.selectOne(MAPPER_URL+"getShopByShopNo",params);
    }
}
