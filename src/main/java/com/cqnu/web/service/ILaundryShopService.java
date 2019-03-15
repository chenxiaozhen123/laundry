package com.cqnu.web.service;

import java.util.Map;

/**
 * @Description 门店
 * @Author xzchen
 * @Date 2019/3/14 17:24
 * @Version 1.0
 **/
public interface ILaundryShopService {
    /**
     * 添加门店
     * @param params
     * @return
     */
    int addLaundryShop(Map<String, Object> params);

    /**
     * 得到门店编号
     * @return
     */
    Map<String, Object> getShopNO();

    /**
     * 删除门店
     * @param params
     * @return
     */
    int deleteLaundryShop(Map<String, Object> params);

    /**
     * 修改门店信息
     * @param params
     * @return
     */
    int updateLaundryShop(Map<String, Object> params);
}
