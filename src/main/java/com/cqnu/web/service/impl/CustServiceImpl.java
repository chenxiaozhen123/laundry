package com.cqnu.web.service.impl;

import com.cqnu.base.service.BaseService;
import com.cqnu.base.service.impl.BaseServiceImpl;
import com.cqnu.web.service.ICustService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description TODO
 * @Author xzchen
 * @Date 2019/1/31 10:40
 * @Version 1.0
 **/
@Service("custService")
public class CustServiceImpl  extends BaseServiceImpl implements ICustService {
    @Override
    public Map<String, Object> login(Map<String, Object> param) {
        Map<String, Object> resMap = session.selectOne("com.cqnu.web.mapper.CustomerMapper.getCustomer",param);
        return resMap;
    }
}
