package com.cqnu.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.model.BaseRes;
import com.cqnu.base.service.BaseService;
import com.cqnu.web.entity.OrderDetail;
import com.cqnu.web.service.IOrderDetailService;
import com.cqnu.web.service.IOrderService;
import com.cqnu.web.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 订单业务层
 * @Author xzchen
 * @Date 2019/3/17 14:58
 * @Version 1.0
 **/
@RestController
@RequestMapping("/admin/order")
public class OrderController {
    private static Logger logger = LoggerFactory.getLogger(OrderController.class);
    private static String calssPath = "com.cqnu.web.controller.OrderController";
    @Autowired
    BaseService baseService;
    @Autowired
    IOrderService orderService;
    @Autowired
    IOrderDetailService orderDetailService;
    /**
     * 创建订单
     */
    @ResponseBody
    @RequestMapping(value = "/add")
    @Transactional
    public BaseRes addOrder(HttpServletRequest request){
        int result = 0;
        try{
            Map<String, Object> reqMap = new HashMap<>();
            Map<String, Object> resMap = new HashMap<>();
            String orderId = StringHelper.getOrderIdStr();
            String shopNO =  request.getParameter("shopNO");
            String appointDate =  request.getParameter("appointDate"); //预约取衣时间
            String price =  request.getParameter("price");
            String remark =  request.getParameter("remark"); //备注
            String addressId =  request.getParameter("addressId");// 地址id
            String custId =  request.getParameter("custId"); //顾客编号
            String goodsStr =  request.getParameter("goodsStr");//订单详情
            String status =  LaundryConsts.WAIT_PAY_STATUS;
            reqMap.put("orderId",orderId);
            reqMap.put("appointDate",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(appointDate));
            reqMap.put("price",Double.valueOf(price));
            reqMap.put("remark",remark);
            reqMap.put("addressId",addressId);
            reqMap.put("shopNO",shopNO);
            reqMap.put("status",status);
            String[] goodsArr = goodsStr.split("}");
            List<OrderDetail> orderDetails = this.getOrderDetailList(goodsArr,custId,orderId);
            result = orderService.addOrder(reqMap);
            if( 0 < result){
                result= 0;
                result = orderDetailService.insertOrderDetailBatch(orderDetails);
                if( 0 < result){
                    resMap.put("orderId",orderId);
                    return BaseRes.getSuccess(resMap);
                } else{
                    logger.error(calssPath+"：处理订单失败");
                    return BaseRes.getException("服务器异常");
                }
            }else {
                return BaseRes.getException("服务器异常");
            }
        }catch (DataAccessException e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("服务器异常");
        }catch (Exception e){
            logger.error(calssPath+"：处理订单失败",e.getMessage());
            return BaseRes.getException("处理订单失败");
        }
    }
    /**
     * 处理订单
     */
    @ResponseBody
    @RequestMapping(value = "/handle")
    public BaseRes handleOrder(HttpServletRequest request){
        int result = 0;
        try{
            Map<String, Object> reqMap = new HashMap<>();
            String orderId =  request.getParameter("orderId");
            String action =  request.getParameter("action");
            reqMap = getStatusByAction(action);
            reqMap.put("orderId",orderId);
            result= orderService.handleOrder(reqMap);
            if( 0 < result){
                return BaseRes.getSuccess();
            }
            else{
                logger.error(calssPath+"：处理订单失败");
                return BaseRes.getFailure("处理订单失败");
            }
        }catch (DataAccessException e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("数据库异常");
        }catch (Exception e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("处理订单失败");
        }
    }
    /**
     * 查询所有订单
     */
    @ResponseBody
    @RequestMapping(value = "/getOrderList")
    public BaseRes getLaundryShopList(HttpServletRequest request){
        Map<String, Object> reqMap = new HashMap<>();
        Map<String, Object> resMap = new HashMap<>();
        long t1 = 0;
        long t2 = 0;
        try{
            String pageNumber =  request.getParameter("pageNumber");
            String pageSize =  request.getParameter("pageSize");
            String status =  request.getParameter("status");
            String orderId =  request.getParameter("orderId");
            if( null != pageNumber && null != pageSize){
                reqMap.put("pageNum",pageNumber);
                reqMap.put("pageSize",pageSize);
            }
            reqMap.put("status",status);
            reqMap.put("orderId",orderId);
            t1 = System.currentTimeMillis();
            resMap = baseService.queryForPage("com.cqnu.web.mapper.OrderMapper.getOrderList",reqMap);
            t2 = System.currentTimeMillis();
        }catch (DataAccessException e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("数据库异常");
        }catch (Exception e){
            logger.error(calssPath+"：查询订单失败",e.getMessage());
            return BaseRes.getException("查询订单失败");
        }

        return BaseRes.getSuccess(getActionByStatus(resMap),t2-t1);
    }
       /**
     * 获取顾客所有订单
     */
    @ResponseBody
    @RequestMapping(value = "/getOrderTotal")
    public BaseRes getOrderTotal(HttpServletRequest request){
        Map<String, Object> reqMap = new HashMap<>();
        Map<String, Object> resMap = new HashMap<>();
        long t1 = 0;
        long t2 = 0;
        try{
            String custId =  request.getParameter("custId");
            reqMap.put("custId",custId);
            t1 = System.currentTimeMillis();
            resMap = baseService.queryForPage("com.cqnu.web.mapper.OrderMapper.getOrderTotal",reqMap);
            t2 = System.currentTimeMillis();
        }catch (DataAccessException e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("数据库异常");
        }catch (Exception e){
            logger.error(calssPath+"：查询订单失败",e.getMessage());
            return BaseRes.getException("查询订单失败");
        }
        return BaseRes.getSuccess(resMap,t2-t1);
    }
    /**
     * 获取订单详情
     */
    @ResponseBody
    @RequestMapping(value = "/getOrderDetail")
    public BaseRes getOrderDetail(HttpServletRequest request){
        Map<String, Object> reqMap = new HashMap<>();
        Map<String, Object> resMap = new HashMap<>();
        long t1 = 0;
        long t2 = 0;
        try{
            String orderId =  request.getParameter("orderId");
            reqMap.put("orderId",orderId);
            t1 = System.currentTimeMillis();
            resMap = baseService.queryForPage("com.cqnu.web.mapper.OrderDetailMapper.getOrderDetail",reqMap);
            t2 = System.currentTimeMillis();
        }catch (DataAccessException e){
            logger.error(calssPath+"：数据库异常",e.getMessage());
            return BaseRes.getException("数据库异常");
        }catch (Exception e){
            logger.error(calssPath+"：获取订单详情失败",e.getMessage());
            return BaseRes.getException("获取订单详情失败");
        }
        return BaseRes.getSuccess(resMap,t2-t1);
    }

    /**
     * 根据门店/中心员工操作取得订单对应的状态
     * @return
     */
    private Map<String, Object> getStatusByAction(String action){
        Map<String, Object> reqMap = new HashMap<>();
        switch(action){
            case LaundryConsts.TAKE_ACTION:
                reqMap.put("takeDesc",LaundryConsts.TAKE_STATUS);
                break;
            case LaundryConsts.SEND_ACTION:
                reqMap.put("sendDesc",LaundryConsts.SEND_STATUS);
                break;
            case LaundryConsts.WASH_ACTION:
                reqMap.put("washDesc",LaundryConsts.WASH_STATUS);
                break;
            case LaundryConsts.HANG_ACTION:
                reqMap.put("hangDesc",LaundryConsts.HANG_STATUS);
                break;
            case LaundryConsts.RECEIVE_ACTION:
                reqMap.put("receiveDesc",LaundryConsts.RECEIVE_STATUS);
                break;
            case LaundryConsts.TAKE_BACK_ACTION:
                reqMap.put("takeBackDesc",LaundryConsts.TAKE_BACK_STATUS);
                break;
            case LaundryConsts.DELIVER_ACTION:
                reqMap.put("deliverDesc",LaundryConsts.WAIT_CONFIRM_STATUS);
                break;
        }
        return reqMap;
    }

    /**
     * 根据订单状态设置员工对订单的操作
     * @param params
     * @return
     */
    private Map<String, Object> getActionByStatus(Map<String, Object> params){
        String action = null;
        String status = null;
        List<Map<String, Object>> list = (List)params.get("rows");
        for(int i = 0;i<list.size();i++){
            status = list.get(i).get("status").toString();
            switch(status){
                case LaundryConsts.WAIT_TAKE_STATUS:
                    action = LaundryConsts.TAKE_ACTION;
                    break;
                case LaundryConsts.TAKE_STATUS:
                    action = LaundryConsts.SEND_ACTION;
                    break;
                case LaundryConsts.SEND_STATUS:
                    action = LaundryConsts.WASH_ACTION;
                    break;
                case LaundryConsts.WASH_STATUS:
                    action = LaundryConsts.HANG_ACTION;
                    break;
                case LaundryConsts.HANG_STATUS:
                    action = LaundryConsts.RECEIVE_ACTION;
                    break;
                case LaundryConsts.RECEIVE_STATUS:
                    action = LaundryConsts.TAKE_BACK_ACTION;
                    break;
                case LaundryConsts.TAKE_BACK_STATUS:
                    action = LaundryConsts.DELIVER_ACTION;
                    break;
            }
            list.get(i).put("action",action);
        }

        params.put("rows",list);
        return params;
    }
    private List<OrderDetail> getOrderDetailList(String[] goodsArr,String custId,String orderId){
        OrderDetail orderDetail = null;
        List<OrderDetail> orderDetails = new ArrayList<>();
        for(int i=0;i<goodsArr.length;i++){
            if(goodsArr[i].startsWith(",")){
                goodsArr[i] = goodsArr[i].substring(1);
            }
            JSONObject jsonObject = (JSONObject) JSONObject.parse(goodsArr[i]+"}");
            orderDetail = new OrderDetail();
            orderDetail.setGoodsNo(jsonObject.get("goods_no").toString());
            orderDetail.setNumber(Integer.valueOf(jsonObject.get("total").toString()));
            orderDetail.setCustId(Integer.valueOf(custId));
            orderDetail.setOrderId(orderId);
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }
}
