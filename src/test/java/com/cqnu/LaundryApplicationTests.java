package com.cqnu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LaundryApplicationTests {

	@Test
	public void contextLoads() {
	}


//	@Test
//	public void sendMsg() throws ClientException {
//		String phoneNumber = "15289918051";
//		String randomNum = createRandomNum(6);
//		String jsonContent = "{\"number\":\"" + randomNum + "\"}";
//
//		Map<String, String> paramMap = new HashMap<>();
//		paramMap.put("phoneNumber", phoneNumber);
//		paramMap.put("msgSign", "干洗联盟");
//		paramMap.put("templateCode", "SMS_160861417");
//		paramMap.put("jsonContent", "12345");
//		SendSmsResponse sendSmsResponse = AliyunMessageUtil.sendSms(paramMap);
//		if(!(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK"))) {
//			if(sendSmsResponse.getCode() == null) {
//				//这里可以抛出自定义异常
//			}
//			if(!sendSmsResponse.getCode().equals("OK")) {
//				//这里可以抛出自定义异常
//			}
//		}
//
//	}
//
//	/**
//	 * 生成随机数
//	 * @param num 位数
//	 * @return
//	 */
//	public static String createRandomNum(int num){
//		String randomNumStr = "";
//		for(int i = 0; i < num;i ++){
//			int randomNum = (int)(Math.random() * 10);
//			randomNumStr += randomNum;
//		}
//		return randomNumStr;
//	}

}

