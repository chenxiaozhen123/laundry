package com.cqnu.web.service;

import java.util.Map;

/**
 * @Description admin表
 * @Author xzchen
 * @Date 2019/3/14 17:27
 * @Version 1.0
 **/
public interface IAdminService {
    /**
     * 添加员工
     * @param params
     * @return
     */
    int addAdmin(Map<String, Object> params);

    /**
     * 得到员工工号
     * @param param
     * @return
     */
    Map<String, Object> getAdminNO(Map<String, Object> param);

    /**
     * 根据工号更改所属门店
     */
    int updateShopIdByAdminNo(Map<String, Object> param);

    /**
     * 根据工号删除员工
     * @param param
     * @return
     */
    int deleteAdminByAdminNo(Map<String, Object> param);

    /**
     * 更改员工个人信息
     * @param param
     * @return
     */
    int updateAdminInfo(Map<String, Object> param);

    /**
     * 管理员更改员工信息
     * @param param
     * @return
     */
    int updateAdminRoleAndShopInfo(Map<String, Object> param);
}
