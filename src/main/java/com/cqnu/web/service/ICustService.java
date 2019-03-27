package com.cqnu.web.service;

import java.util.Map;

/**
 * @Description TODO
 * @Author xzchen
 * @Date 2019/1/31 10:39
 * @Version 1.0
 **/
public interface ICustService {
    Map<String, Object> custLogin(Map<String, Object> param);

    int custRegister(Map<String, Object> param);

    /**
     * 更新顾客信息
     * @param params
     * @return
     */
    int updateCustomer(Map<String, Object> params);
}
