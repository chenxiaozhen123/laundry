package com.cqnu.web.service.impl;

import com.cqnu.base.service.impl.BaseServiceImpl;
import com.cqnu.web.service.IGoodsService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description goods表service的实现类
 * @Author xzchen
 * @Date 2019/3/20 4:33
 * @Version 1.0
 **/
@Service("goodsService")
public class GoodsServiceImpl extends BaseServiceImpl implements IGoodsService{
    private static final String MAPPER_URL = "com.cqnu.web.mapper.GoodsMapper.";
    @Override
    public int addGoods(Map<String, Object> param) {
        return session.insert(MAPPER_URL+"addGoods",param);
    }

    @Override
    public int deleteGoods(Map<String, Object> param) {
        return session.update(MAPPER_URL+"deleteGoods",param);
    }

    @Override
    public int updateGoods(Map<String, Object> param) {
        return session.update(MAPPER_URL+"updateGoods",param);
    }

    @Override
    public Map<String, Object> getMaxGoodsNo() {
        return session.selectOne(MAPPER_URL+"getMaxGoodsNo");
    }

    @Override
    public int deleteGoodsList(Map<String, Object> param) {
        return session.update(MAPPER_URL+"deleteGoodsList",param);
    }
}
