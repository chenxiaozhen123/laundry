package com.cqnu.web.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.common.exception.LaundryException;
import com.cqnu.base.service.BaseService;
import com.cqnu.base.util.AESUtil;
import com.cqnu.web.service.ICustService;
import com.cqnu.web.util.AliyunMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CustRegisterController {

    @Autowired
    ICustService custService;
    @Autowired
    BaseService baseService;
    @Autowired
    AliyunMessageUtil aliyunMessageUtil;

    /**
     * 注册控制
     */
    @ResponseBody
    @RequestMapping(value = "/register")
    public int custRegister(HttpServletRequest request) {
        int result = 0;

        try {
            int usefulTime = Integer.valueOf(request.getParameter("usefulTime"));
            if (usefulTime > 0) {
                int code = Integer.valueOf(request.getParameter("code"));
                Integer codeT = Integer.valueOf(request.getParameter("codeT"));

                if (code == codeT) {
                    String cname = request.getParameter("cname");
                    String password = request.getParameter("password");
                    String sex = request.getParameter("sex");
                    String mobile = request.getParameter("mobile");
                    Map<String, Object> reqMap = new HashMap<>();
                    reqMap.put("cname", cname);
                    reqMap.put("password", AESUtil.aesEncrypt(password, LaundryConsts.CUSTOMER));
                    reqMap.put("sex", sex);
                    reqMap.put("mobile", mobile);
                    result = custService.custRegister(reqMap);
                } else {
                    result = 0;
                }
            } else {
                result = 0;
            }

        } catch (Exception e) {
            throw new LaundryException(e.getMessage());
        }
        return result;
    }

    /**
     * 发送验证码控制
     * @param request
     * @return flag
     * @throws ClientException
     */
    @ResponseBody
    @RequestMapping(value = "/sendSMS")
    public boolean sendSMS(HttpServletRequest request) throws ClientException {
        boolean flag = false;
        try {
            //发送验证码
            String phoneNumber = request.getParameter("mobile");
            String codeSMS = request.getParameter("codeT");
            String jsonContent = "{\"code\":\"" + codeSMS + "\"}";
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("phoneNumber", phoneNumber);
            paramMap.put("jsonContent", jsonContent);
            SendSmsResponse sendSmsResponse = aliyunMessageUtil.sendSms(paramMap);
            if (null != sendSmsResponse.getCode()) {
                flag = true;
            }
        } catch (Exception e) {

        }
        return flag;
    }
}
