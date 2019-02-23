package com.cqnu.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description TODO
 * @Author xzchen
 * @Date 2019/2/23 20:54
 * @Version 1.0
 **/
@Configuration
public class LaundryConfig {
    @Value("${spring.profiles.active}")
    private String devMode;

    /**
     * 是否开发环境
     *
     * @return
     */
    public boolean isDevelopEnv() {
        if (devMode != null && devMode.equals("dev")) {
            return true;
        } else {
            return false;
        }
    }
}
