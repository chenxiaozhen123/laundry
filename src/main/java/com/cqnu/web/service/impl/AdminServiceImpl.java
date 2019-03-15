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
    @Override
    public int addAdmin(Map<String, Object> params) {
        return session.insert("com.cqnu.web.mapper.AdminMapper.addAdmin",params);
    }

    @Override
    public Map<String, Object> getAdminNO(Map<String, Object> param) {
        return session.selectOne("com.cqnu.web.mapper.AdminMapper.getAdminNoByRoleID",param);
    }

    @Override
    public int updateShopIdByAdminNo(Map<String, Object> param) {
        return session.update("com.cqnu.web.mapper.AdminMapper.updateShopIdByAdminNo",param);
    }
}
