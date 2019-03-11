package com.cqnu.web.interceptor;

import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.common.exception.LaundryException;

import org.apache.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Description 登录拦截器
 * @Author xzchen
 * @Date 2019/2/4 12:54
 * @Version 1.0
 **/
public class LoginInterceptor implements HandlerInterceptor {
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//
//    }
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws IOException {
////        Object user = request.getSession().getAttribute(LaundryConsts.SESSION_USER_KEY);
//        // 检查每个到来的请求对应的session域中是否有登录标识
//        Object loginName = request.getSession().getAttribute("loginName");
//        if (null == loginName || !(loginName instanceof String)) {
//            // 未登录，重定向到登录页
//            response.sendRedirect("/login");
//            return false;
//        }
//        String userName = (String) loginName;
//        System.out.println("当前用户已登录，登录的用户名为： " + userName);
//        return true;
//
//    }
}
