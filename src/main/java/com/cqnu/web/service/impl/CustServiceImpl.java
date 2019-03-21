package com.cqnu.web.service.impl;

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
@Service
public class CustServiceImpl extends BaseServiceImpl implements ICustService {
    @Override
    public Map<String, Object> custLogin(Map<String, Object> param) {
        Map<String, Object> resMap = session.selectOne("com.cqnu.web.mapper.CustomerMapper.getCustomer",param);
        return resMap;
    }

    @Override
    public int custRegister(Map<String, Object> param) {
        int resMap = session.insert("com.cqnu.web.mapper.CustomerMapper.addCustpmer",param);
        return resMap;
    }
}

