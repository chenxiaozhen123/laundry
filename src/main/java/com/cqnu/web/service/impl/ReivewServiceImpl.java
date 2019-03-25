package com.cqnu.web.service.impl;

import com.cqnu.base.service.impl.BaseServiceImpl;
import com.cqnu.web.service.IReviewService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description 评价
 * @Author xzchen
 * @Date 2019/3/25 1:07
 * @Version 1.0
 **/
@Service("reviewService")
public class ReivewServiceImpl extends BaseServiceImpl implements IReviewService{
    private static final String MAPPER_URL = "com.cqnu.web.mapper.ReviewMapper.";
    @Override
    public int reviewOrder(Map<String, Object> param) {
        return session.insert(MAPPER_URL+"saveReview",param);
    }
}
