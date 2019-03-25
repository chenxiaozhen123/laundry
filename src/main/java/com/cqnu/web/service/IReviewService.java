package com.cqnu.web.service;

import java.util.Map;

/**
 * @Description 评价
 * @Author xzchen
 * @Date 2019/3/25 1:06
 * @Version 1.0
 **/
public interface IReviewService {
    /**
     * 评价订单
     * @param param
     * @return
     */
    int reviewOrder(Map<String,Object> param);
}
