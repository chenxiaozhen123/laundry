package com.cqnu.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    /**
     * 登陆
     */
    @GetMapping("/login")
    public String login() { return "/admin/login";}

    /**
     * 首页
     */
    @GetMapping("/index")
    public String index() { return "/admin/index";}
}
