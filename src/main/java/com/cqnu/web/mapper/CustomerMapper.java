package com.cqnu.web.mapper;

import com.cqnu.web.entity.Customer;
import com.cqnu.web.util.WebMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//public interface CustomerMapper{
//    //调用xml方式
//    public List<Customer> find();
//
//    //调用注解方法
//    @Select("select * from customer where cust_id=#{id}")
//    public Customer get(@Param("id") Integer id);
//
//}


public interface CustomerMapper extends WebMapper<Customer> {
}