package com.cqnu.web.service.impl;

import com.cqnu.base.service.impl.BaseServiceImpl;
import com.cqnu.web.service.IAdminService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description 员工
 * @Author xzchen
 * @Date 2019/3/14 17:29
 * @Version 1.0
 **/
@Service("adminService")
public class AdminServiceImpl extends BaseServiceImpl implements IAdminService{
    private static final String MAPPER_URL = "com.cqnu.web.mapper.AdminMapper.";
    @Override
    public int addAdmin(Map<String, Object> params) {
        return session.insert(MAPPER_URL+"addAdmin",params);
    }

    @Override
    public Map<String, Object> getAdminNO(Map<String, Object> param) {
        return session.selectOne(MAPPER_URL+"getAdminNoByRoleID",param);
    }

    @Override
    public int updateShopIdByAdminNo(Map<String, Object> param) {
        return session.update(MAPPER_URL+"updateShopIdByAdminNo",param);
    }

    @Override
    public int deleteAdminByAdminNo(Map<String, Object> param) {
        return session.update(MAPPER_URL+"deleteAdminByAdminNo",param);
    }

    @Override
    public int updateAdminInfo(Map<String, Object> param) {
        return session.update(MAPPER_URL+"updateAdminInfo",param);
    }

    @Override
    public int updateAdminRoleAndShopInfo(Map<String, Object> param) {
        return session.update(MAPPER_URL+"updateAdminRoleAndShopInfo",param);
    }

    @Override
    public Map<String, Object> getShopCategory(Map<String, Object> param) {
        return session.selectOne(MAPPER_URL+"getShopCategory",param);
    }
}
