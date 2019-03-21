package com.cqnu.web.service.impl;

import com.cqnu.base.service.impl.BaseServiceImpl;
import com.cqnu.web.service.ICategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @Description category表的service实现层
 * @Author xzchen
 * @Date 2019/3/19 9:50
 * @Version 1.0
 **/
@Service("categoryService")
public class CategoryServiceImpl extends BaseServiceImpl implements ICategoryService{
    private static final String MAPPER_URL = "com.cqnu.web.mapper.CategoryMapper.";
    @Override
    public int addCategory(Map<String, Object> param) {
        return session.insert(MAPPER_URL+"addCategory",param);
    }

    @Override
    public int updateCategory(Map<String, Object> param) {
        return session.update(MAPPER_URL+"updateCategory",param);
    }

    @Override
    @Transactional
    public int deleteCategory(Map<String, Object> param) {
        return session.update(MAPPER_URL+"deleteCategory",param);
    }

    @Override
    public Map<String, Object> getCatNo() {
        return session.selectOne(MAPPER_URL+"getCatNo");
    }
}
