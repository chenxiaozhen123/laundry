package com.cqnu.web.util;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 字符串类型工具
 * @Author xzchen
 * @Date 2019/3/15 11:49
 * @Version 1.0
 **/
public class StringHelper {
    /**
     * 将重复的数据从String去除
     *
     * @param str
     *            String 源数据

     * @return String 去除后的数据
     */
    public static String delRepeatData(String str) {
        ArrayList al = new ArrayList();
        StringBuffer dataBuf = new StringBuffer();
        if (str != null && str.length() >= 1) {
            StringTokenizer st = new StringTokenizer(str, ",");
            while (st.hasMoreTokens()) {
                al.add(st.nextToken());
            }
            String singleDateArr[] = delRepeatData(al);
            if (singleDateArr != null) {
                for (int i = 0; i < singleDateArr.length; i++) {
                    dataBuf.append(singleDateArr[i]);
                    if (i != singleDateArr.length - 1) {
                        dataBuf.append(",");
                    }
                }
            }

        }
        return dataBuf.toString();
    }
    /**
     * 将重复的数据从ArrayList去除
     *
     * @param al 源数据
     *
     * @return String 去除后的数据
     */

    public static String[] delRepeatData(ArrayList al) {
        Set set = new HashSet(al);
        Object objs[] = set.toArray(new String[0]);
        return (String[]) objs;
    }

    /**
     * 将string类型封装成List
     * @param data
     * @return
     */
    public static List<String> stringToList(String data){
        String dataStr = delRepeatData(data);
        String str[] = dataStr.split(",");
        return Arrays.asList(str);
    }

    /**
     * 获取订单编号
     * @return
     */
    public static String getOrderIdStr(){
        Date now = new Date();
        String reqTime = new SimpleDateFormat("yyyyMMddHHmmss").format(now);
        UUID uuid = UUID.randomUUID();
        int num = uuid.toString().hashCode();
        if (num < 0) {
            num = -num;
        }
        String serialNum = String.format("%09d", num).substring(0,5);
        String orderId = reqTime+serialNum;
        return  orderId;
    }
}
