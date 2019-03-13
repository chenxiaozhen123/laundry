package com.cqnu.web.service;

import java.util.Map;

/**
 * @Description 权限角色
 * @Author xzchen
 * @Date 2019/3/12 17:17
 * @Version 1.0
 **/
public interface IRoleService {
    Map<String, Object> getRole(Map<String, Object> param);
}
