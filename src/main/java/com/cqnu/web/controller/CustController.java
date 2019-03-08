package com.cqnu.web.controller;

import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.model.Message;
import com.cqnu.base.util.MailUtil;
import com.cqnu.web.service.ICustService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    MailUtil mailUtil;
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

        String to = "a569876412@qq.com";
        String su = "账号信息";
        String content = "测试邮件";

//        mailUtil.sendMail(to,su,content);
        Message message = new Message();
        message.setUsername("陈");
        message.setGender(LaundryConsts.GENTLEMAN);
        message.setInitalPassword(LaundryConsts.INITIAL_PASSWORD);
        message.setJobNumber("00270000001");
        mailUtil.sendMailAccountMsg(message,to,su,"accountMsg.ftl");
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("mobile","13368342442");
        reqMap.put("password","123456");
        Map<String, Object> resMap = custService.login(reqMap);
        logger.debug(resMap.toString());
    }

}
