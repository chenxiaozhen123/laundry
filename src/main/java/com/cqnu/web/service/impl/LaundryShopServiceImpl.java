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
    @Override
    public int addLaundryShop(Map<String, Object> params) {
        return session.insert("com.cqnu.web.mapper.LaundryShopMapper.addLaundryShop",params);
    }

    @Override
    public Map<String, Object> getShopNO() {
        return session.selectOne("com.cqnu.web.mapper.LaundryShopMapper.getShopNo");
    }

    @Override
    public int deleteLaundryShop(Map<String, Object> params) {
        return session.update("com.cqnu.web.mapper.LaundryShopMapper.deleteLaundryShop",params);
    }

    @Override
    public int updateLaundryShop(Map<String, Object> params) {
        return session.update("com.cqnu.web.mapper.LaundryShopMapper.updateLaundryShop",params);
    }

    @Override
    public Map<String, Object> getShopByShopNo(Map<String, Object> params) {
        return session.selectOne("com.cqnu.web.mapper.LaundryShopMapper.getShopByShopNo",params);
    }
}
