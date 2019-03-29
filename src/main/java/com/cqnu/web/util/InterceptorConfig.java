package com.cqnu.web.util;

import com.cqnu.base.config.LaundryConfig;
import com.cqnu.web.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @Description 拦截器
 * @Author xzchen
 * @Date 2019/2/23 18:10
 * @Version 1.0
 **/
@Configuration
public class InterceptorConfig  implements WebMvcConfigurer {
    @Autowired
    LaundryConfig laundryConfig;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (laundryConfig.isDevelopEnv()) {
            // 开发环境不设置拦截器
            return;
        }
        // 注册拦截器
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        InterceptorRegistration loginRegistry = registry.addInterceptor(loginInterceptor);
        // 拦截路径
        loginRegistry.addPathPatterns("/**");
        // 排除路径
        loginRegistry.excludePathPatterns("/index.html"); //启动欢迎页
        loginRegistry.excludePathPatterns("/admin/login.html"); //后台登录页
        loginRegistry.excludePathPatterns("/admin/sys/login"); //后台登录页请求地址
        loginRegistry.excludePathPatterns("/login.html"); //前台登录页请求地址
        loginRegistry.excludePathPatterns("/admin/order/update"); //支付完成更新订单

        // 排除资源请求
        loginRegistry.excludePathPatterns("/static/css/*.css");
        loginRegistry.excludePathPatterns("/static/img/*.png");
        loginRegistry.excludePathPatterns("/static/bootstrap/css/*.css");
        loginRegistry.excludePathPatterns("/static/bootstrap/fonts/**");
        loginRegistry.excludePathPatterns("/static/bootstrap/js/*.js");
        loginRegistry.excludePathPatterns("/static/jquery/*.js");
        loginRegistry.excludePathPatterns("/static/js/*.js");
    }



}
