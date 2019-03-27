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
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC+hq8mBtmc0g8SzD6K+VzgG7604LkJesC765bv4j+vzKdmKWglQyv3EB6CgnFwYIP+OV7bLcuyR2482YW5gghs87JJN5CFRY49u+czfqCVi3iGXI4D+1yWmwC/i8MNhlhbTpv/K1YiWOEC7Ul27fNWgetyQdF1i2tAi44CUSOr1f8stIGgRRi7jFYJoVGbNufGt2ARhQu29jBu6KieT3cnm24QJS/siErgB/iAXDdg0PSQodMUf5Z+pj43GxfAKZe/kola96vIPxhm3d5bDSI0nSlxKgFDERxxzC7bPhLL/d8RCHFbb7HveGu2cEFFNxkiA6/XRrzPpRqMdJXxWt77AgMBAAECggEAXUjkiOHhun6/Ep0eIj1l0/TBsx40ewe3RktbY6iLNjAl5kdvzo7M//LMfWoQ0Mzw05CTeQ07JtcCipiWePXJ2k6XhHHIOPiNZHyT/wqI5a+cG21Fu31fH/5Up3Ru1DhrGomqp404ZyjNQvmbdrea5UhHV3ISdFMG+Ftb9JZ1uLP+DhJMrpO0bXuCsMTiuoWsuSQsQ6w0KMcEhAnGWQpML6WRC2x7Mv/59YdgTp76mfT293J/9pd16XO3+1++t1evdGyHOsKR78inIr7g+GoataiLhP2gtQUiXQhNYKaE3j+9WxEbTbAnherK1sq76jwyMylbMlfTwXebjyuIgDyuAQKBgQDkvxluW5MXfPWnCJ2cm5NWDU7EY50pDzEc7wHxtrsL+8RCYK+KwhNqKkpeYOncAiRx2pcq1k/F+D3tzc2a5yxUJWmtDYRcj7NEw8iJxURXQnbfepRygM5BZipdaIiTnEzRcl7a9dFripeKUGqsaN7W9Vsfjqw/UnNv+pi9LjMjIQKBgQDVOddeEErXLLfe90CyAOmG4i9xLGb2FQMSQzfApbUuxxyK1vyUxGA5EBr+mTrBi5wZr0O4ksJBfLFyDdgpX1pEFHq33UvkbHqJ9Nu7KBv+tZ40ql5eKtKoOU8lbvnfZUR1gO6474PwDg8E//xegaY3dQHNpzsmzhi8EUJ6p6tamwKBgQC1eSpa1AnA86EL0BkCeqaL+WjJA2JWhbfoBXHcaqTAIBAWvQ/nHIs8iaqQb4B4y7ZQISQWlwmYvJWdCM4aG1aet0O8R+H/cP2/CzgYnMjYFWGrcFs+8BMx7TSlAF6CBNkovPyl56kqETyiPaQzfct0bBjL02uxnN76MqxkQbfOIQKBgQDTN/D35Pu+GzYpUJRtwCmUzApi+bEko7vfI2bfF0dbhzHm9wBN/hmzbRjQi8mAYGPJraxP8lErvfGpIb23fhVUy1SlPjonlK3uavgfdaQFtLN7SCki7xiuO9cDJeOwQAiIvae82sa/za1jLpE4rQWSef3HUplt30rxQcew6Nb9cQKBgCFGwnilW8bpimrDHI/X6yJr5At9Cx+xsmnHmsMvPtkl0hk2mQyLf+OX9f3jOq3N7Iauh6iEZPnIk5Fq5WMKwT3F7nBOi030Vv0+Ix52JIRGKMs0a83r1HAvHLxeocCsTRigfCuvskh2l3BTONlkjYc2w5rkNdKLHFOXDHqzc7Gt";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8D8UCzPnTSy8DNtl5LyeHJa8XMAha7NDbvHGeCIdxCW8LQ3V5y9s/IGxLBlPoXs1kdtqEt+dO85ltASJLKtQSryrlI2qqxcBOU4ETTzGX5gFLHfTwueUq7rkjvuWfi2nTDo0zMR12ZxRM0PL7sTsRr1kRyp9LAxwZKA2haHYkwFBGI4ssbFGBDJ99ag3OlshVf8FHVS68bAM+JJnMm89hpWScG57lMjJYFZeIS8RMyIwiZ9wichh8jGLvpmr3LtJPzzZYGkcjnkrgePGaMIw7/BVgKoNCFcRzRtkcBmkLyLvSYwIChIQWlhzWNY4Q5lfqZmW4CAiE8WdzJ5ZbBNtEQIDAQAB";
    
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = " http://www.lengy.xin/jsp/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = " http://www.lengy.xin/admin/order/update";

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

