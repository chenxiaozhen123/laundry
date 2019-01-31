package com.cqnu.base.service.impl;

import com.cqnu.base.mapper.BaseMapper;
import com.cqnu.base.service.BaseService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author xzchen
 * @Date 2019/1/31 10:20
 * @Version 1.0
 **/
public class BaseServiceImpl implements BaseService{
    @Autowired
    protected SqlSessionTemplate session;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected BaseMapper baseMapper;

    public BaseMapper getBaseMapper() {
        return this.baseMapper;
    }

    public SqlSession getSqlSession() {
        return this.session;
    }

    public List<Map<String, Object>> queryForList(String key, Map<String, Object> param) {
        return session.selectList(key, param);
    }

    public Map<String, Object> queryForMap(String key, Map<String, Object> param) {
        return session.selectOne(key, param);
    }

    public Map<String, Object> queryForPage(String key, Map<String, Object> param) {
        Map<String, Object> retmap = new HashMap<String, Object>();
        boolean pagination = false;
        if (param.containsKey("page") && param.containsKey("rows")) {
            pagination = true;
        }
        if (pagination) {
            PageHelper.startPage(param);
            List<Map<String, Object>> list = session.selectList(key, param);

            retmap.put("rows", list);
            retmap.put("total", ((Page<?>) list).getTotal());
        } else {
            List<Map<String, Object>> list = session.selectList(key, param);
            retmap.put("rows", list);
        }
        return retmap;
    }


    public int insert(String key, Map<String, Object> param) {
        return session.insert(key, param);
    }

    public int update(String key, Map<String, Object> param) {
        return session.update(key, param);
    }

    public int delete(String key, Map<String, Object> param) {
        return session.delete(key, param);
    }

    // TODO 这个方法目前没用到
    public List<Map<String, Object>> callProcedure(Map<String, Object> param) {

        return null;
    }
}
