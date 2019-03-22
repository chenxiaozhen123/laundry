package com.cqnu.web.controller;

import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.common.exception.LaundryException;
import com.cqnu.base.service.BaseService;
import com.cqnu.web.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    BaseService baseService;
    @Autowired
    IOrderService orderService;
    /**
     * 处理订单
     */
    @ResponseBody
    @RequestMapping(value = "/handle")
    public int handleOrder(HttpServletRequest request){
        int result = 0;
        try{
            Map<String, Object> reqMap = new HashMap<>();
            String orderId =  request.getParameter("orderId");
            String action =  request.getParameter("action");
            reqMap = getStatusByAction(action);
            reqMap.put("orderId",orderId);
            result= orderService.handleOrder(reqMap);
        }catch (Exception e){
            throw new LaundryException(e.getMessage());
        }
        return result;
    }
    /**
     * 查询所有订单
     */
    @ResponseBody
    @RequestMapping(value = "/getOrderList")
    public Map<String, Object> getLaundryShopList(HttpServletRequest request){
        Map<String, Object> reqMap = new HashMap<>();
        Map<String, Object> resMap = new HashMap<>();
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
            resMap = baseService.queryForPage("com.cqnu.web.mapper.OrderMapper.getOrderList",reqMap);
        }catch (Exception e){
            throw new LaundryException(e.getMessage());
        }

        return getActionByStatus(resMap);
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
}
