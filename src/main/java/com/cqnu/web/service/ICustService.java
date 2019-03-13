package com.cqnu.web.service;

import com.cqnu.web.entity.Customer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author xzchen
 * @Date 2019/1/31 10:39
 * @Version 1.0
 **/
public interface ICustService {
    Map<String, Object> login(Map<String, Object> param);


//    public List<Customer> find();
//    public Customer get(Integer id);
}
