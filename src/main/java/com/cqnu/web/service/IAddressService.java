package com.cqnu.web.service;

import java.util.Map;

/**
 * @Description 地址
 * @Author xzchen
 * @Date 2019/3/24 11:09
 * @Version 1.0
 **/
public interface IAddressService {
    /**
     * 新增地址
     * @param parma
     * @return
     */
    int addAddress(Map<String,Object> parma);

    /**
     * 修改地址
     * @param param
     * @return
     */
    int updateAddress(Map<String,Object> param);

    /**
     * 获取顾客的地址
     * @param param
     * @return
     */
    Map<String,Object> getCustAddress(Map<String,Object> param);
}
