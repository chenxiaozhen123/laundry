package com.cqnu.web.util;

import org.springframework.context.annotation.Configuration;
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
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        super.addInterceptors(registry);
    }


}
