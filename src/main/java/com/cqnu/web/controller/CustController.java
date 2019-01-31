package com.cqnu.web.controller;

import com.cqnu.web.service.ICustService;
import com.cqnu.web.service.impl.CustServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author xzchen
 * @Date 2019/1/31 10:50
 * @Version 1.0
 **/
@RestController
@RequestMapping("/cust")
public class CustController {
    private Logger logger = LoggerFactory.getLogger(CustController.class);
    @Autowired
    ICustService custService;
    /**
     * 用户查询: 本网3张表 + 它网用户
     * 客户ID，房间号
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login")
    public void login(HttpServletRequest request) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("mobile","13368342442");
        reqMap.put("password","123456");
        Map<String, Object> resMap = custService.login(reqMap);
        logger.debug(resMap.toString());
    }

}
