package com.cqnu.web.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.model.BaseRes;
import com.cqnu.base.service.BaseService;
import com.cqnu.base.util.AESUtil;
import com.cqnu.web.service.ICustService;
import com.cqnu.web.util.AliyunMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CustRegisterController {
    private static Logger logger = LoggerFactory.getLogger(CustRegisterController.class);
    private static String calssPath = "com.cqnu.web.controller.CustRegisterController";

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
    public BaseRes custRegister(HttpServletRequest request) {
        int result = 0;

        try {
            int usefulTime = Integer.valueOf(request.getParameter("usefulTime"));
            if (usefulTime > 0) {
                int code = Integer.valueOf(request.getParameter("code"));
                Integer codeT = Integer.valueOf(request.getParameter("codeT"));
                if (code == codeT) {
                    String mobile = request.getParameter("mobile");
                    boolean flag = this.getMobile(mobile);
                    if(flag == false){
                        String cname = request.getParameter("cname");
                        String password = request.getParameter("password");
                        Map<String, Object> reqMap = new HashMap<>();
                        reqMap.put("cname", cname);
                        reqMap.put("password", AESUtil.aesEncrypt(password, LaundryConsts.CUSTOMER));
                        reqMap.put("mobile", mobile);
                        result = custService.custRegister(reqMap);
                    }else {
                        logger.error(calssPath + "：号码已存在");
                        return BaseRes.getFailure("号码已存在");
                    }
                } else {
                    logger.error(calssPath + "：验证失败");
                    return BaseRes.getFailure("验证失败");
                }
            } else {
                logger.error(calssPath + "：验证超时");
                return BaseRes.getFailure("验证超时");
            }
            if (0 < result) {
                return BaseRes.getSuccess();
            } else {
                logger.error(calssPath + "：修改信息失败");
                return BaseRes.getFailure("修改信息失败");
            }
        } catch (DataAccessException e) {
            logger.error(calssPath + "：数据库异常", e.getMessage());
            return BaseRes.getException("数据库异常");
        } catch (Exception e) {
            logger.error(calssPath + "：数据库异常", e.getMessage());
            return BaseRes.getException("修改信息失败");
        }
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
            System.out.println("数据库异常");
        }
        return flag;
    }

    /**
     * 查询号码唯一
     */
    private boolean getMobile(String mobile) {
        boolean flag = false;
        Map<String, Object> resMap =  new HashMap<>();
        try {
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("mobile", mobile);
            resMap = baseService.queryForMap(LaundryConsts.CUSTOMERMAPPER_URL + "selectMobile", reqMap);
            if (null != resMap) {
                flag = true;
            }
        }catch (Exception e){

        }
        return flag;
    }


}
