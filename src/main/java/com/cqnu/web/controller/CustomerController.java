package com.cqnu.web.controller;

import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.model.BaseRes;
import com.cqnu.base.service.BaseService;
import com.cqnu.base.util.AESUtil;
import com.cqnu.web.service.ICustService;
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

/**
 * @Description TODO
 * @Author xzchen
 * @Date 2019/1/31 10:50
 * @Version 1.0
 **/
@RestController
@RequestMapping("/customer")
public class CustomerController {
    private static Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private static String calssPath = "com.cqnu.web.controller.CustomerController";
    @Autowired
    ICustService custService;
    @Autowired
    BaseService baseService;

    /**
     * 修改密码控制
     */
    @ResponseBody
    @RequestMapping(value = "/updateCustomer")
    public BaseRes updateCustomer(HttpServletRequest request) {
        int result = 0;
        try {
            int usefulTime = Integer.valueOf(request.getParameter("usefulTime"));
            if (usefulTime > 0) {
                int code = Integer.valueOf(request.getParameter("code"));
                Integer codeT = Integer.valueOf(request.getParameter("codeT"));
                if (code == codeT) {
                    String password = request.getParameter("password");
                    String mobile = request.getParameter("mobile");
                    Map<String, Object> reqMap = new HashMap<>();
                    reqMap.put("password", AESUtil.aesEncrypt(password, LaundryConsts.CUSTOMER));
                    reqMap.put("mobile", mobile);
                    result = custService.updateCustomer(reqMap);
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

}




