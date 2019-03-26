package com.cqnu.base.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016092800616013";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCTjRJmqqrccoXpS7V8ynxF2LBF4yJqF1BYzuORf4XvVaxgFwPWsllFxvnf1wJYyeZLASfAYnC2vB2h8UCQpmarFrHVJd4ISbbojVe+7FKx2Lfaqci1v5WHImssObcdufQNg0UidSDlk8fvH9ALxTLeuc3Udocx46fqS3xXKfE1gCjoE3C/Np1h4AObFizMdEdXjcDGK/EStmgd6Do1otmaG9fmzx5n248rJgj3Sq85E3IB72PJsoFUBunkYnoI2ZNJ9wcKn6e8huHAxhhy6mELnNmq5iuWen3+D+dEpi9cF8TPVbsqNzaX6zLrHCZkXbKnqTKMAyGmBnqwE/49hiBdAgMBAAECggEBAI8QYZ8aK8DDagPiXH469nPivkeFC4FCSa1SLGSO+RYuQ4yHxDkPhG+blfvrRWNBoAFphVmMGS2VCEZU3P1j2nhY8ORyau4uSyMAqOt9yFCYiOuDZBCVSi5UuQWQkHRKMNBJT2cxmkv/FgRNHDPNaJ18Go6+vicpTU5nEpCRIkd0GV+vWDUZTCZ9gezac/XLezkw7lSgKJncdV6F09fXNZatQVr5CqvijHZ0A6vY8QKJnH1CNthFur0+d6zndPXiEzRfG0myIvMw2euOyPJ4D6YuwlQUg52rwHxy/mefI7uJFtgueFEv8wnpOFYOGJatEJH19SI1ql4n5imadvvT9gECgYEA1yA2A09YAYCUxH9yAXQmgzD4ahwaDBRXS95kPPNxhrZOkqr+8EOV1GtCGX57Bl9IaQhiJYaNAJGaFxVhnQNWsg949JlCIIPy7fKalhXrkIjWSdmHEj6mRnhN8uxhlVtM6smj3rBikyBNOJL33lkrdQzfxufECwG709CckbN/xHECgYEAr5YAuCjcksrKfMk2ZFNweTKvWYro8RqVlN8i1vJpemlyWbsUELp5Q2YxgOzBO71Kotf5QqrwkdyGt14bQH4u0g35Wg3JQM9ddrRRpqYWjyf9vKNJquIg/swqVbMymprMY5r+RpPGP0IAhu7SU7EPNcbZw7l5eFi8eDLHD6DEYK0CgYEAjkOS9KVoAbZSq4/MiQGS5ndgEGv9Hbfl8xpjF5u2m+u8EmpapfFMCGnWEJ4AJbqlXDBbF43feVzQlithnkAy+RYaW0du36ni4VDqwg5E9hRIAhaEqKAdvcSZZ+jCr87tEue+Iupqhfl6l8s5eCVoA8Q7yIJEtzKyT+VJZWFbQUECgYBecP4+D6xkQDMdX1SkLcUHS13Jec/FLxlZk5M3rfdR4jmhTeCDBumGeQcTY0SkDzJ/2E63rx3qNoU3R6S80aH5VY7TAytGcsLAwEhSrG7h3Q67UdCj2zEIG2SB5/rF8ZER2iH+cClniOajrPzwCwI1WJl0juRo+MGWnSk4nK5DmQKBgCkINoPQyLF3FS6mJfaiibR4yeirvsjsuWeMVQ73A/qyNgFwdp6CY4mk9vz5UQMmcfrlA+SsWe2vxwuIvHk45TA1yvz+mp7tMS7j95DwnBb7QPpIaD9ZOTQ2njytYKFYMTQ9hWYszCAKVC9vVMSS2Tp1EjlObXoNkv5dcRDcS6L0";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8D8UCzPnTSy8DNtl5LyeHJa8XMAha7NDbvHGeCIdxCW8LQ3V5y9s/IGxLBlPoXs1kdtqEt+dO85ltASJLKtQSryrlI2qqxcBOU4ETTzGX5gFLHfTwueUq7rkjvuWfi2nTDo0zMR12ZxRM0PL7sTsRr1kRyp9LAxwZKA2haHYkwFBGI4ssbFGBDJ99ag3OlshVf8FHVS68bAM+JJnMm89hpWScG57lMjJYFZeIS8RMyIwiZ9wichh8jGLvpmr3LtJPzzZYGkcjnkrgePGaMIw7/BVgKoNCFcRzRtkcBmkLyLvSYwIChIQWlhzWNY4Q5lfqZmW4CAiE8WdzJ5ZbBNtEQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://5r9dcm.natappfree.cc/jsp/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://5r9dcm.natappfree.cc/admin/login.html";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

