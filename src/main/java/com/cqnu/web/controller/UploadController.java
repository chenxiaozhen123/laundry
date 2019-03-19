package com.cqnu.web.controller;

import com.cqnu.base.config.LaundryConfig;
import com.cqnu.web.util.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Description 上传文件控制器
 * @Author xzchen
 * @Date 2019/3/19 21:03
 * @Version 1.0
 **/
@RestController
@RequestMapping("/admin/upload")
public class UploadController {
    @Autowired
    OSSUtil ossUtil;
    @Autowired
    LaundryConfig laundryConfig;

    /**
     * 上传图片
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/img" ,method = RequestMethod.POST)
    public Map<String, Object> uploadApkFile(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> resMap = new HashMap<String, Object>();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        /** 页面控件的文件流* */
        MultipartFile multipartFile = null;
        Map map =multipartRequest.getFileMap();
        for (Iterator i = map.keySet().iterator(); i.hasNext();) {
            Object obj = i.next();
            multipartFile=(MultipartFile) map.get(obj);
        }
        String fileUrl = "";
        try {
            fileUrl = ossUtil.checkImage(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        resMap.put("fileUrl", fileUrl);
        resMap.put("message", "应用上传成功");
        resMap.put("status", true);
        return resMap;
    }
    
}
