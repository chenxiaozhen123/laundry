package com.cqnu.web.controller;

import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.model.BaseRes;
import com.cqnu.base.service.BaseService;
import com.cqnu.web.service.IOrderService;
import com.cqnu.web.service.IReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 订单评价
 * @Author xzchen
 * @Date 2019/3/25 14:11
 * @Version 1.0
 **/
@RestController
@RequestMapping("/admin/review")
public class ReviewController {
    private static Logger logger = LoggerFactory.getLogger(ReviewController.class);
    private static String calssPath = "com.cqnu.web.controller.ReviewController";
    private static final String MAPPER_URL = "com.cqnu.web.mapper.ReviewMapper.";
    @Autowired
    IReviewService reviewService;
    @Autowired
    IOrderService orderService;
    @Autowired
    BaseService baseService;
    /**
     * 评价
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    @Transactional
    public BaseRes addReview(HttpServletRequest request) {
        int result = 0;
        try{
            String custId =  request.getParameter("custId");
            String orderId = request.getParameter("orderId");
            String rate = request.getParameter("rate");
            String content =  request.getParameter("content");
            Map<String, Object> reqMap = new HashMap<>();
            Map<String, Object> reqOrderMap = new HashMap<>();
            reqMap.put("custId",custId);
            reqMap.put("orderId",orderId);
            reqMap.put("rate",LaundryConsts.REVIEW_RATE_DESC.get(rate));
            reqMap.put("content", content);
            //修改订单状态
            reqOrderMap.put("orderId",orderId);
            reqOrderMap.put("orderOver",LaundryConsts.ORDER_OVER);
            result = reviewService.reviewOrder(reqMap);
            if( 0 < result){
                result = 0;
                result= orderService.handleOrder(reqOrderMap);
                if( 0 < result){
                    return BaseRes.getSuccess();
                }
                else{
                    logger.error(calssPath+"：评价订单失败");
                    return BaseRes.getFailure("评价失败");
                }
            }else{
                logger.error(calssPath+"：评价订单失败");
                return BaseRes.getFailure("评价订单失败");
            }
        }catch (DataAccessException e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("数据库异常");
        }catch (Exception e){
            logger.error(calssPath+"：评价订单失败",e.getMessage());
            return BaseRes.getException("评价订单失败");
        }
    }
    /**
     * 门店业务近一个月的订单情况
     */
    @ResponseBody
    @RequestMapping(value = "/getReviewList")
    public BaseRes getReviewList(HttpServletRequest request){
        Map<String, Object> reqMap = new HashMap<>();
        Map<String, Object> resMap = new HashMap<>();
        long t1 = 0;
        long t2 = 0;
        try{
            String rate =  request.getParameter("rate");
            String pageNumber =  request.getParameter("pageNumber");
            String pageSize =  request.getParameter("pageSize");
            if( null != pageNumber && null != pageSize){
                reqMap.put("pageNum",pageNumber);
                reqMap.put("pageSize",pageSize);
            }
            reqMap.put("rate",rate);
            t1 = System.currentTimeMillis();
            resMap = baseService.queryForPage(MAPPER_URL+"getReviewList",reqMap);
            t2 = System.currentTimeMillis();
        }catch (DataAccessException e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("数据库异常");
        }catch (Exception e){
            logger.error(calssPath+"：获取订单信息失败",e.getMessage());
            return BaseRes.getException("获取订单信息失败");
        }
        return BaseRes.getSuccess(resMap,t2-t1);
    }


}
