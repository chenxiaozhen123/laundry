package com.cqnu.web.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.cqnu.base.common.exception.LaundryException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Description 阿里云上传文件工具类
 * @Author xzchen
 * @Date 2019/3/20 3:33
 * @Version 1.0
 **/
@Component
public class OSSUtil {
    protected static final Logger log = LoggerFactory.getLogger(OSSUtil.class);

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.access-id}")
    private String accessKeyId;
    @Value("${aliyun.oss.access-key}")
    private String accessKeySecret;
    @Value("${aliyun.oss.bucket}")
    private String bucketName;

    //图片文件存储目录
    private String fileImgDir = "images/";

    /**
     *
     * 上传图片
     * @param file
     * @return
     */
    public String uploadImg2Oss(MultipartFile file) {
        if (file.getSize() > 1024 * 1024 *30) {
            return "图片太大";
        }
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        try {
            InputStream inputStream = file.getInputStream();
            this.uploadFile2OSS(inputStream, name);
            return name;
        } catch (Exception e) {
            throw new LaundryException("上传失败");
        }
    }



    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }

    /**
     * 获取图片路径
     * @param fileUrl
     * @return
     */
    private String getImgUrl(String fileUrl) {
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            String url =  this.getUrl(this.getFilePath() + split[split.length - 1]);
            return url;
        }
        return null;
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        // 生成URL
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }


    /**
     * 多图片上传
     * @param fileList
     * @return
     */
    public String checkList(List<MultipartFile> fileList) {
        String  fileUrl = "";
        String  str = "";
        String  photoUrl = "";
        for(int i = 0;i< fileList.size();i++){
            fileUrl = uploadImg2Oss(fileList.get(i));
            str = getImgUrl(fileUrl);
            if(i == 0){
                photoUrl = str;
            }else {
                photoUrl += "," + str;
            }
        }
        return photoUrl.trim();
    }

    /**
     * 单个图片上传
     * @param file
     * @return
     */
    public String checkImage(MultipartFile file){
        String fileUrl = uploadImg2Oss(file);
        String str = getImgUrl(fileUrl);
        return str.trim();
    }
    /**
     * 上传图片获取fileUrl
     * @param instream
     * @param fileName
     * @return
     */
    private String uploadFile2OSS(InputStream instream, String fileName) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            PutObjectResult putResult = ossClient.putObject(bucketName, this.getFilePath() + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 设置文件目录
     * @return
     */
    private String getFilePath(){
        Calendar calender = Calendar.getInstance();
        int year =  calender.get(Calendar.YEAR);
        int month = calender.get(Calendar.MONTH)+1;
        StringBuilder saveFilePath = new StringBuilder("");
        String path = saveFilePath.append(year).append(month).toString();
        return this.fileImgDir+path+"/";
    }
}
