package com.cqnu.web.interceptor;

import com.cqnu.base.common.consts.LaundryConsts;
import com.cqnu.base.common.exception.LaundryException;

import com.cqnu.web.entity.Admin;
import org.apache.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws IOException {
        Object obj = request.getSession().getAttribute(LaundryConsts.SESSION_USER_KEY);
        //会话session判断
        Admin admin = (Admin) obj;
        if (admin == null) {
            response.setStatus(HttpStatus.SC_MOVED_PERMANENTLY);
//            throw LaundryException.getSessionTimeoutException("session timeout"); // 直接抛出异常
            PrintWriter out = response.getWriter();
            StringBuilder builder = new StringBuilder();
            builder.append("<script type=\"text/javascript\" charset=\"utf-8\">");
            builder.append("alert(decodeURI('%E4%BC%9A%E8%AF%9D%E5%B7%B2%E8%B6%85%E6%97%B6%EF%BC%8C%E8%AF%B7%E9%87%8D%E6%96%B0%E7%99%BB%E5%BD%95'));");
            builder.append("window.top.location.href=\"").append(request.getContextPath()).append("/admin/login.html\"");
            builder.append("</script>");
            out.print(builder.toString());
            out.close();
        }
        return true;


    }
}
