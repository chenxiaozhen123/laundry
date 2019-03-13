package com.cqnu.web.service.impl;

import com.cqnu.base.service.BaseService;
import com.cqnu.base.service.impl.BaseServiceImpl;
import com.cqnu.web.entity.Customer;
import com.cqnu.web.mapper.CustomerMapper;
import com.cqnu.web.service.ICustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author xzchen
 * @Date 2019/1/31 10:40
 * @Version 1.0
 **/
@Service
//public class CustServiceImpl implements ICustService {
//    @Autowired
//    private CustomerMapper customerMapper;
//
//    @Override
//    public List<Customer> find() {
//        return customerMapper.find();
//    }
//
//    @Override
//    public Customer get(Integer id) {
//        return customerMapper.get(id);
//    }
////    @Override
////    public Map<String, Object> login(Map<String, Object> param) {
////        Map<String, Object> resMap = session.selectOne("com.cqnu.web.mapper.CustomerMapper.getCustomer",param);
////        return resMap;
////    }
//
//
//
//}

public class CustServiceImpl  extends BaseServiceImpl implements ICustService {
    @Override
    public Map<String, Object> login(Map<String, Object> param) {
        Map<String, Object> resMap = session.selectOne("com.cqnu.web.mapper.CustomerMapper.getCustomer",param);
        return resMap;
    }



}

