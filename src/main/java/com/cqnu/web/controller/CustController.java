package com.cqnu.web.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @Description TODO
 * @Author xzchen
 * @Date 2019/1/31 10:50
 * @Version 1.0
 **/
@RestController
//@RequestMapping("/cust")
public class CustController {

    //模拟用户登录验证
    @RequestMapping(value = "/login/{username}/{password}",method = RequestMethod.GET)
    public Object login(@PathVariable String username,@PathVariable String password){
        System.out.println(username);
        System.out.println(password);
        return "success";
    }

    //模拟现实用户信息
    @RequestMapping(value = "/list")
    public Object list(){
        String username = "root";
        String password = "123456";
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>(1);
        objectObjectHashMap.put("username",username);
        objectObjectHashMap.put("password",password);
        return objectObjectHashMap;
    }





//    private Logger logger = LoggerFactory.getLogger(CustController.class);
//
//    @Autowired
//    ICustService custService;
//    @RequestMapping("/login/{password}")
//    public String Login(HttpServletRequest request,@PathVariable Integer password){
//        Map<String, Object> reqMap = new HashMap<>();
//        reqMap.put("mobile","13368342442");
//        reqMap.put("password",password);
//        Map<String, Object> resMap = custService.login(reqMap);
//        logger.debug(resMap.toString());
//        return "/index";
//    }


//    @Autowired
//    ICustService custService;
//
//    @RequestMapping("/find")
//    public List<Customer> find(){
//        return custService.find();
//    }
//
//    @RequestMapping("/get/{id}")
//    public Customer get(@PathVariable Integer id){
//        return custService.get(id);
//    }



//    @Autowired
//    MailUtil mailUtil;
//    /**
//     * 用户查询: 本网3张表 + 它网用户
//     * 客户ID，房间号
//     *
//     * @param request
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/login")
//    public void login(HttpServletRequest request) {
//
//        String to = "763268418@qq.com";
//        String su = "账号信息";
//        String content = "测试邮件";
//
////        mailUtil.sendMail(to,su,content);
//        Message message = new Message();
//        message.setUsername("陈");
//        message.setGender(LaundryConsts.GENTLEMAN);
//        message.setInitalPassword(LaundryConsts.INITIAL_PASSWORD);
//        message.setJobNumber("00270000001");
//        mailUtil.sendMailAccountMsg(message,to,su,"accountMsg.ftl");
//        Map<String, Object> reqMap = new HashMap<>();
//        reqMap.put("mobile","13368342442");
//        reqMap.put("password","123456");
//        Map<String, Object> resMap = custService.login(reqMap);
//        logger.debug(resMap.toString());
//    }

}
