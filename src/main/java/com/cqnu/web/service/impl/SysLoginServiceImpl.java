package com.cqnu.web.service.impl;

import com.cqnu.base.service.impl.BaseServiceImpl;
import com.cqnu.web.service.ISysLoginService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description TODO
 * @Author xzchen
 * @Date 2019/3/12 17:11
 * @Version 1.0
 **/
@Service("sysLoginService")
public class SysLoginServiceImpl extends BaseServiceImpl implements ISysLoginService{
    private static final String MAPPER_URL = "com.cqnu.web.mapper.AdminMapper.";
    @Override
    public Map<String, Object> getAdmin(Map<String, Object> param) {
        Map<String, Object> resMap = session.selectOne(MAPPER_URL + "getAdmin",param);
        return resMap;
    }
}
