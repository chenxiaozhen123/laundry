package com.cqnu.web.service.impl;

import com.cqnu.base.service.impl.BaseServiceImpl;
import com.cqnu.web.service.IRoleService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description TODO
 * @Author xzchen
 * @Date 2019/3/12 17:17
 * @Version 1.0
 **/
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl implements IRoleService{
    private static final String MAPPER_URL = "com.cqnu.web.mapper.RoleMapper.";
    @Override
    public Map<String, Object> getRole(Map<String, Object> param) {
        Map<String, Object> resMap = session.selectOne(MAPPER_URL + "getRole",param);
        return resMap;
    }
}
