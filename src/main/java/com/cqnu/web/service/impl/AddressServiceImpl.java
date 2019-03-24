package com.cqnu.web.service.impl;

import com.cqnu.base.service.impl.BaseServiceImpl;
import com.cqnu.web.service.IAddressService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description TODO
 * @Author xzchen
 * @Date 2019/3/24 11:13
 * @Version 1.0
 **/
@Service("addressService")
public class AddressServiceImpl extends BaseServiceImpl implements IAddressService {
    private static final String MAPPER_URL = "com.cqnu.web.mapper.AddressMapper.";
    @Override
    public int addAddress(Map<String, Object> parma) {
        return session.insert(MAPPER_URL+"addAddress",parma);
    }

    @Override
    public int updateAddress(Map<String, Object> param) {
        return session.update(MAPPER_URL+"update",param);
    }

    @Override
    public Map<String, Object> getCustAddress(Map<String, Object> param) {
        return session.selectOne(MAPPER_URL+"getCustAddress",param);
    }
}
