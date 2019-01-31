package com.cqnu.base.service;

import com.cqnu.base.mapper.BaseMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author xzchen
 * @Date 2019/1/31 10:20
 * @Version 1.0
 **/
public interface BaseService {
    BaseMapper getBaseMapper();
    SqlSession getSqlSession();
    /**
     * 查询列表
     * @param key
     * @param param
     * @return
     */
    List<Map<String, Object>> queryForList(String key, Map<String, Object> param);

    /**
     * 查询单条数据
     * @param key
     * @param param
     * @return
     */
    Map<String, Object> queryForMap(String key, Map<String, Object> param);

    /**
     * 分页查询-数据和总数{"rows":,"total":}
     * @param key
     * @param param
     * @return
     */
    Map<String, Object> queryForPage(String key, Map<String, Object> param);

    /**
     * 新增
     * @param key
     * @param param
     * @return
     */
    int insert(String key, Map<String, Object> param);

    /**
     * 修改
     * @param key
     * @param param
     * @return
     */
    int update(String key, Map<String, Object> param);

    /**
     * 删除
     * @param key
     * @param param
     * @return
     */
    int delete(String key, Map<String, Object> param);

    /**
     * 执行存储过程
     * @param param
     * @return
     */
    List<Map<String, Object>> callProcedure(Map<String, Object> param);
}
