package com.cqnu.web.interceptor;

import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.common.exception.LaundryException;

import org.apache.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 登录拦截器
 * @Author xzchen
 * @Date 2019/2/4 12:54
 * @Version 1.0
 **/
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        Object user = request.getSession().getAttribute(LaundryConsts.SESSION_USER_KEY);
        if(null == user){
            response.setStatus(HttpStatus.SC_MOVED_PERMANENTLY);
            throw LaundryException.getSessionTimeoutException("session timeout");
        }
        return true;
    }
}
